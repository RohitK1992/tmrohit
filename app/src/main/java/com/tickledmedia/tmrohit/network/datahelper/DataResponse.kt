package com.tickledmedia.tmrohit.network.datahelper

import androidx.annotation.NonNull

class DataResponse<ResultType> {

    val data: ResultType?

    val status: Status

    val errorResponse: ErrorResponse?

    val errorCode: Int

    constructor(@NonNull data: ResultType) {
        this.status = Status.SUCCESS
        this.data = data
        this.errorCode = -1
        this.errorResponse = null
    }

    constructor(@NonNull status: Status) {
        this.status = status
        this.data = null
        this.errorCode = -1
        this.errorResponse = null
    }

    constructor(@NonNull status: Status, errorResponse: ErrorResponse) {
        this.status = status
        this.data = null
        this.errorCode = -1
        this.errorResponse = errorResponse
    }

    constructor(errorCode: Int, errorResponse: ErrorResponse) {
        this.status = Status.ERROR
        this.data = null
        this.errorCode = errorCode
        this.errorResponse = errorResponse
    }

    enum class Status {
        NETWORK_FETCH_SUCCESS, SUCCESS, ERROR, LOADING
    }
}