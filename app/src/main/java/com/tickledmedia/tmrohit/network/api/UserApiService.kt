package com.tickledmedia.tmrohit.network.api

import androidx.lifecycle.LiveData
import com.tickledmedia.tmrohit.models.ResponseItem
import com.tickledmedia.tmrohit.network.datahelper.DataResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface UserApiService {

    @GET
    fun getList(@Url url: String): LiveData<DataResponse<List<ResponseItem>>>
}