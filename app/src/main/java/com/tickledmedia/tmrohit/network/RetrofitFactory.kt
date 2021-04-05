package com.tickledmedia.tmrohit.network

import androidx.annotation.NonNull
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tickledmedia.tmrohit.BuildConfig
import com.tickledmedia.tmrohit.MyApplication
import com.tickledmedia.tmrohit.utils.BaseUrlConstants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


internal object RetrofitFactory {

    private var retrofit: Retrofit? = null
    private var gson : Gson = GsonBuilder().create()

    //  Retrofit need it for building, will be handled dynamically with @Url annotation
    private val retrofitClient: Retrofit
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BaseUrlConstants.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
                    .build()
            }
            return retrofit!!
        }

    private val okHttpClient: OkHttpClient
        @NonNull
        get() {
            val builder = OkHttpClient.Builder()
            builder.addInterceptor(headerAndUrlInterceptor)
            builder.addInterceptor(httpLoggingInterceptor)
            builder.addInterceptor(ChuckerInterceptor.Builder(MyApplication.application)
                    .collector(ChuckerCollector(MyApplication.application))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build())
            builder.dns(DnsSelector(DnsSelector.Mode.IPV4_FIRST))
            builder.connectTimeout(60, TimeUnit.SECONDS) // connect timeout
            builder.readTimeout(60, TimeUnit.SECONDS)    // socket timeout
            builder.retryOnConnectionFailure(true)
            return builder.build()
        }


    private val httpLoggingInterceptor: HttpLoggingInterceptor
        @NonNull
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            } else {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
            }

            return httpLoggingInterceptor
        }

    private// Request customization: add request headers
    val headerAndUrlInterceptor: Interceptor
        get() = Interceptor { chain ->
            val original = chain.request()
            //  ADD COMMON HEADER
            val requestBuilder = original.newBuilder()
                .addHeader("App-Version", BuildConfig.VERSION_CODE.toString().replace(".", ""))
                .addHeader("Client-Os", "Android")
            /*if (UserManager.getIsRegistered() && !TextUtils.isEmpty(LoginManager.getOauthToken())) {
                requestBuilder.addHeader("darthvader", LoginManager.getOauthToken())
            }*/

            //  ADD COMMON URL QUERY PARAMETERS
            val originalHttpUrl = original.url
            /*if (!originalHttpUrl.toString().contains("googleapis.com")) {
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter(UrlConstants.CLIENT_OS_KEY, UrlConstants.CLIENT_OS_VALUE)
                    .addQueryParameter(
                        "app_version",
                        BuildConfig.VERSION_CODE.toString().replace(".", "")
                    )
                    .addQueryParameter("device_id", PreferenceManager.getAppPreference().deviceId)
                    .build()
                requestBuilder.url(url)
            } else {
                // Request customization: add request url
                requestBuilder.url(originalHttpUrl)
            }*/

            requestBuilder.url(originalHttpUrl)
            val request = requestBuilder.build()
            chain.proceed(request)
        }

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofitClient.create(serviceClass)
    }

}