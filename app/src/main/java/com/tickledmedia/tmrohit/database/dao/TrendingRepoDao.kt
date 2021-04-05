package com.tickledmedia.tmrohit.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tickledmedia.tmrohit.constants.TableConstants
import com.tickledmedia.tmrohit.models.ResponseItem

@Dao
interface TrendingRepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllRepositories(responseItem: List<ResponseItem>)

    @Transaction
    @Query("SELECT * FROM " + TableConstants.TRENDING_REPO + " ORDER BY ID DESC LIMIT 1")
    fun getSingleRepo() : LiveData<ResponseItem>

    @Transaction
    @Query("SELECT * FROM " + TableConstants.TRENDING_REPO + " group by author,name")
    fun getLocalRepoList() : LiveData<List<ResponseItem>>

    @Transaction
    @Query("DELETE FROM " + TableConstants.TRENDING_REPO)
    fun deleteData()
}