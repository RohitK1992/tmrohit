package com.tickledmedia.tmrohit.network

import android.net.Uri
import com.tickledmedia.tmrohit.utils.BaseUrlConstants

object UrlProvider {

    fun getList() : String{
        val uri = Uri.parse(BaseUrlConstants.BASE_URL + "repositories")
            .buildUpon()
            .build()

        return uri.toString()
    }
}