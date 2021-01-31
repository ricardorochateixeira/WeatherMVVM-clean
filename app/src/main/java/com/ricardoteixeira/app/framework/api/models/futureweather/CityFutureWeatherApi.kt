package com.ricardoteixeira.app.framework.api.models.futureweather

import com.squareup.moshi.Json

data class CityFutureWeatherApi(
    @Json(name = "country")
    var country: String?,
    @Json(name = "id")
    var id: Int?,
    @Json(name = "name")
    var name: String?,
    @Json(name = "population")
    var population: Int?,
    @Json(name = "sunrise")
    var sunrise: Int?,
    @Json(name = "sunset")
    var sunset: Int?,
    @Json(name = "timezone")
    var timezone: Int?
)