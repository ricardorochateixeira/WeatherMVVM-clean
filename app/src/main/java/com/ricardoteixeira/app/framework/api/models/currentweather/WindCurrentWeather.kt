package com.ricardoteixeira.app.framework.api.models.currentweather

import com.squareup.moshi.Json


data class WindCurrentWeather(
    @Json(name = "deg") var deg: Int?,

    @Json(name = "speed") var speed: Double?
)