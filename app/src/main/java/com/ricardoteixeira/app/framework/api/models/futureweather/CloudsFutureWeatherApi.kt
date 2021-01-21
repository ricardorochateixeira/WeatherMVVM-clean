package com.ricardoteixeira.app.framework.api.models.futureweather

import com.squareup.moshi.Json

data class CloudsFutureWeatherApi(
    @Json(name = "all")
    var all: Int?
)
