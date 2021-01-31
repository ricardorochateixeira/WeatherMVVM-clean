package com.ricardoteixeira.utils

data class Data<RequestData>(
    var responseType: Status,
    var data: RequestData? = null,
    var ror: Exception? = null
)

enum class Status { SUCCESSFUL, ERROR, LOADING }