package com.ricardoteixeira.app.framework.api.models.futureweather

import com.squareup.moshi.Json

data class MainFutureWeatherApi(
    @Json(name = "feels_like")
    var feelsLike: Double?,
    @Json(name = "grnd_level")
    var grndLevel: Int?,
    @Json(name = "humidity")
    var humidity: Int?,
    @Json(name = "pressure")
    var pressure: Int?,
    @Json(name = "sea_level")
    var seaLevel: Int?,
    @Json(name = "temp")
    var temp: Double?,
    @Json(name = "temp_kf")
    var tempKf: Double?,
    @Json(name = "temp_max")
    var tempMax: Double?,
    @Json(name = "temp_min")
    var tempMin: Double?
)
