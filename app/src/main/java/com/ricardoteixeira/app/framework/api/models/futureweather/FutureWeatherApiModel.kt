package com.ricardoteixeira.app.framework.api.models.futureweather


import com.squareup.moshi.Json

data class FutureWeatherApiModel(
    @Json(name = "city")
    var cityFutureWeatherApi: CityFutureWeatherApi?,
    @Json(name = "cnt")
    var cnt: Int?,
    @Json(name = "cod")
    var cod: String?,
    @Json(name = "list")
    var list: List<GeneralFutureWeatherApi?>?,
    @Json(name = "message")
    var message: Int?
)

