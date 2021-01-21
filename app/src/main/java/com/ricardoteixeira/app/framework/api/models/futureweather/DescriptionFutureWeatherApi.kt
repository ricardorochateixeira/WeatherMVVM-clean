package com.ricardoteixeira.app.framework.api.models.futureweather

import com.squareup.moshi.Json

data class DescriptionFutureWeatherApi(
    var description: String?,
    var icon: String?,
    var id: Int?,
    var main: String?
)