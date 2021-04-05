package com.tickledmedia.tmrohit.network.datahelper

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class ErrorResponse {

    @SerializedName("code")
    @Expose
    var code: Int = -1

    @SerializedName("error_code")
    @Expose
    var errorCode: Int = -1

    @SerializedName("business_error_code")
    @Expose
    var businessErrorCode: Int = -1

    @SerializedName("message")
    @Expose
    var message: String? = null


    @SerializedName("success")
    @Expose
    var success: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null


    var errorScreenType: Int = ErrorScreenConstants.NO_SCREEN
}