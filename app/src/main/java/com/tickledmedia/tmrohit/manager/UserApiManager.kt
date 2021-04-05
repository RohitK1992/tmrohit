package com.tickledmedia.tmrohit.manager

import androidx.lifecycle.LiveData
import com.tickledmedia.tmrohit.models.ResponseItem
import com.tickledmedia.tmrohit.network.UrlProvider
import com.tickledmedia.tmrohit.network.configs.ServiceProvider
import com.tickledmedia.tmrohit.network.datahelper.DataResponse

object UserApiManager {

    @JvmStatic
    fun getList(): LiveData<DataResponse<List<ResponseItem>>> {
        return ServiceProvider.getUserApiService()
            .getList(UrlProvider.getList())
    }
}