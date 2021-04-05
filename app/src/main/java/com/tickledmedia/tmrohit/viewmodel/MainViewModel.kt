package com.tickledmedia.tmrohit.viewmodel

import androidx.lifecycle.LiveData
import com.tickledmedia.tmrohit.manager.DbManager
import com.tickledmedia.tmrohit.manager.UserManager
import com.tickledmedia.tmrohit.models.ResponseItem
import com.tickledmedia.tmrohit.network.datahelper.DataResponse

open class MainViewModel : BaseViewModel() {

    fun getListFromServer(): LiveData<DataResponse<List<ResponseItem>>>{
        return UserManager.getList()
    }

    fun insertAllRepositories(listData : List<ResponseItem>) {
        DbManager.addRepositories(listData)
    }

    fun getSingleRepoFromDatabase() : LiveData<ResponseItem>{
        return DbManager.getSingleRepo()
    }

    fun getLocalRepoList() : LiveData<List<ResponseItem>>{
        return DbManager.getLocalRepoList()
    }

    fun deleteLocalRepo(){
        return DbManager.removeLocalData()
    }

}