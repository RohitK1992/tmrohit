package com.tickledmedia.tmrohit.manager

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import com.tickledmedia.tmrohit.models.ResponseItem
import com.tickledmedia.tmrohit.network.NetworkResource
import com.tickledmedia.tmrohit.network.datahelper.DataResponse

object UserManager {

    fun getList(): LiveData<DataResponse<List<ResponseItem>>> {
        return object : NetworkResource<List<ResponseItem>>() {
            @NonNull
            override fun createCall(): LiveData<DataResponse<List<ResponseItem>>> {
                return UserApiManager.getList()
            }
        }.apiResultLiveData
    }

}