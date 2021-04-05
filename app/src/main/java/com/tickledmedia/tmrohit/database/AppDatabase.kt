package com.tickledmedia.tmrohit.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tickledmedia.tmrohit.database.DbConstants.APP_DB
import com.tickledmedia.tmrohit.database.dao.TrendingRepoDao
import com.tickledmedia.tmrohit.models.ResponseItem

@Database(
    entities = [
        ResponseItem::class
    ], version = DbConstants.APP_DB_VERSION, exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun trendingRepoDao(): TrendingRepoDao

    companion object {

        private lateinit var instance: AppDatabase

        fun getInstance(): AppDatabase {
            return instance
        }

        internal fun initAppDatabase(context: Context) {
            instance =
                Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, APP_DB)
                    .fallbackToDestructiveMigration()
                    .build()
        }
    }

}