package com.ricardoteixeira.app.framework.api.models.futureweather

import com.squareup.moshi.Json

data class SysFutureWeatherApi(
    @Json(name = "pod")
    var pod: String?
)
