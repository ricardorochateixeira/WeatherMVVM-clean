package com.ricardoteixeira.utils

import android.app.DownloadManager

data class Data<RequestData>(var responseType: Status, var data: RequestData? = null, var ror: Exception? = null)

enum class Status {SUCCESSFUL, ERROR, LOADING}