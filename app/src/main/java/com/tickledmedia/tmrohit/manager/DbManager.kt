package com.tickledmedia.tmrohit.manager

import androidx.lifecycle.LiveData
import com.tickledmedia.tmrohit.database.AppDatabase
import com.tickledmedia.tmrohit.executors.AppExecutors
import com.tickledmedia.tmrohit.models.ResponseItem

internal object DbManager {

    @Synchronized
    fun addRepositories(listData: List<ResponseItem>) {
        AppExecutors().diskIO().execute {
            AppDatabase.getInstance().trendingRepoDao().insertAllRepositories(listData)
        }
    }

    @Synchronized
    fun getSingleRepo() : LiveData<ResponseItem> {
        return AppDatabase.getInstance().trendingRepoDao().getSingleRepo()
    }

    @Synchronized
    fun getLocalRepoList() : LiveData<List<ResponseItem>> {
        return AppDatabase.getInstance().trendingRepoDao().getLocalRepoList()
    }

    @Synchronized
    fun removeLocalData() {
        AppExecutors().diskIO().execute {
            AppDatabase.getInstance().trendingRepoDao().deleteData()
        }
    }
}