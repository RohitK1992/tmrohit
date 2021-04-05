package com.tickledmedia.tmrohit.network

import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import com.google.gson.GsonBuilder
import com.tickledmedia.tmrohit.BuildConfig
import com.tickledmedia.tmrohit.network.datahelper.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class LiveDataCallAdapter<R> internal constructor(private val responseType: Type) :
    CallAdapter<R, LiveData<DataResponse<R>>> {

    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(@NonNull call: Call<R>): LiveData<DataResponse<R>> {

        return object : LiveData<DataResponse<R>>() {
            override fun onActive() {
                super.onActive()

                call.clone().enqueue(object : Callback<R> {
                    override fun onResponse(
                        @NonNull call1: Call<R>,
                        @NonNull response: Response<R>
                    ) {
                        if (call1.isCanceled) return

                        if (response.isSuccessful) {
                            postValue(DataResponse(response.body()!!))
                        } else {
                            var errorResponse = ErrorResponse()
                            errorResponse.errorCode = response.code()
                            if (response.errorBody() != null) {
                                logApiErrorEvent(call1, response)
                                try {
                                    errorResponse = getErrorResponse(response.errorBody()!!)
                                    errorResponse.errorCode = response.code()
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    errorResponse = ErrorResponse()
                                    errorResponse.errorCode = ErrorCodeConstants.SERVER_UNAVAILABLE
                                }
                            }
                            errorResponse.errorScreenType = getErrorScreen(
                                errorResponse.errorCode,
                                errorResponse.businessErrorCode
                            )
                            postValue(DataResponse(response.code(), errorResponse))
                        }
                    }

                    override fun onFailure(@NonNull call1: Call<R>, @NonNull t: Throwable) {
                        logApiFailureEvent(call1, t)
                        if (BuildConfig.DEBUG) {
                            Log.e("GSON PARSING ERROR:", t.message)
                        }
                        if (call1.isCanceled) return
                        val errorResponse = ErrorResponse()

                        if (t is IOException || t is SocketTimeoutException || t is UnknownHostException) {
                            val networkErrorCode = ErrorCodeConstants.NETWORK
                            errorResponse.errorCode = networkErrorCode
                            errorResponse.errorScreenType = getErrorScreen(
                                errorResponse.errorCode,
                                errorResponse.businessErrorCode
                            )
                            postValue(DataResponse(networkErrorCode, errorResponse))
                        } else {
                            val badReqErrorCode = ErrorCodeConstants.BAD_REQUEST
                            errorResponse.errorCode = badReqErrorCode
                            errorResponse.errorScreenType = getErrorScreen(
                                errorResponse.errorCode,
                                errorResponse.businessErrorCode
                            )
                            postValue(DataResponse(badReqErrorCode, errorResponse))
                        }
                    }
                })
            }
        }
    }

    private fun getErrorScreen(errorCode: Int, businessErrorCode: Int): Int {
        return when (errorCode) {
            ErrorCodeConstants.BAD_REQUEST -> {
                when (businessErrorCode) {
                    BusinessErrorConstants.TRY_AFTER_TIME -> ErrorScreenConstants.NO_SCREEN
                    else -> ErrorScreenConstants.GENERIC
                }
            }
            ErrorCodeConstants.SERVER_MAINTENANCE,
            ErrorCodeConstants.SERVER_BUG,
            ErrorCodeConstants.TOO_MANY_REQUEST,
            ErrorCodeConstants.FORBIDDEN,
            ErrorCodeConstants.NOT_FOUND -> ErrorScreenConstants.GENERIC
            else -> ErrorScreenConstants.GENERIC
        }
    }

    private fun logApiErrorEvent(call1: Call<R>, response: Response<R>) {
        try {

            var url = call1.request().url.toString()
            var message = response.errorBody().toString()
            if (url.length >= 90) {
                url = url.substring(0, 90)
            }
            if (message.length >= 90) {
                message = message.substring(0, 90)
            }

        } catch (exception: Exception) {

        }
    }

    private fun logApiFailureEvent(call1: Call<R>, t: Throwable) {
        try {

            var url = call1.request().url.toString()
            var message = t.message ?: ""
            if (url.length >= 90) {
                url = url.substring(0, 90)
            }
            if (message.length >= 90) {
                message = message.substring(0, 90)
            }

        } catch (exception: IllegalArgumentException) {

        }
    }

    @Throws(Exception::class)
    private fun getErrorResponse(responseBody: ResponseBody): ErrorResponse {
        return GsonBuilder().create().fromJson(responseBody.string(), ErrorResponse::class.java)
    }
}