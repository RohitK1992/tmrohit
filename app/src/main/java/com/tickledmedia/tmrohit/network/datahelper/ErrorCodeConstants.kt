package com.tickledmedia.tmrohit.network.datahelper

object ErrorCodeConstants {
    const val WFH = -1
    const val NETWORK = 0
    const val BAD_REQUEST = 400
    const val UNAUTHORIZED_TOKEN = 401
    const val NOT_FOUND = 404
    const val FORBIDDEN = 403
    const val TOO_MANY_REQUEST = 420
    const val FORCE_UPGRADE = 426
    const val SERVER_BUG = 500
    const val SERVER_MAINTENANCE = 502
    const val SERVER_UNAVAILABLE = 503
}

object BusinessErrorConstants {
    //  Business Errors
    const val STORE_NOT_FOUND = 1101
    const val INVALID_MOBILE_NO = 1201
    const val RESEND_OTP = 1202
    const val TRY_AFTER_TIME = 1203
    const val INVALID_OTP = 1204
    const val OTP_EXPIRED = 1205
    const val INVALID_REFERRAL_CODE = 1206
    const val INVALID_CART_ID = 1301
    const val CART_PRICE_MISMATCH = 1302
    const val INVALID_COUPON = 1304
    const val INVALID_CREDIT_APPLIED = 1305
    const val INVALID_ADDRESS_IN_CART = 1306
    const val COUPON_EXPIRED = 1307
    const val INSUFFICIENT_COUPON_AMOUNT = 1308
    const val CRM_ESCALATION_FAILED = 1401
    const val CRM_ESCALATION_FAILED_ON_IMMEDIATE_REQUEST = 400
    const val COUPON_INEFFICIENT_PRODUCT = 301
}