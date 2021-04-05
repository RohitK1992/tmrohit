package com.tickledmedia.tmrohit

import android.app.Application
import com.tickledmedia.tmrohit.database.initDatabases

open class MyApplication : Application() {

    companion object {
        lateinit var application: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        initDatabases(applicationContext)
    }

}