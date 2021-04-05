package com.tickledmedia.tmrohit.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ForceUpdate {

    @SerializedName("is_force_update")
    @Expose
    var isForceUpdate: Boolean = false

    @SerializedName("url")
    @Expose
    var url: String? = ""

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("callcenter_phone_number")
    @Expose
    var callcenterPhoneNumber: Long? = null

    fun getForceUpdate(): Boolean {
        return isForceUpdate
    }
}