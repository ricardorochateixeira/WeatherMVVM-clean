package com.ricardoteixeira.app.framework.api.models

import com.squareup.moshi.Json


data class Wind(
    @Json(name = "deg") var deg: Int?,

    @Json(name = "speed") var speed: Double?
)