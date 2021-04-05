package com.tickledmedia.tmrohit.network.configs

import com.tickledmedia.tmrohit.network.RetrofitFactory
import com.tickledmedia.tmrohit.network.api.UserApiService

object ServiceProvider {
    private var userApiService: UserApiService? = null

    fun getUserApiService(): UserApiService {
        if (userApiService == null) {
            userApiService = RetrofitFactory.createService(UserApiService::class.java)
        }
        return userApiService!!
    }

}