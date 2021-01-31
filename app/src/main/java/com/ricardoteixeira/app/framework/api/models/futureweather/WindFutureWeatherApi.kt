package com.ricardoteixeira.app.framework.api.models.futureweather

import com.squareup.moshi.Json

data class WindFutureWeatherApi(
    @Json(name = "deg")
    var deg: Int?,
    @Json(name = "speed")
    var speed: Double?
)