package com.ricardoteixeira.app.framework.api.models

import com.squareup.moshi.Json

data class WeatherCityApiModel(

    @Json(name = "cod") var cod: Int?,

    @Json(name = "coord") var coord: Coord?,

    @Json(name = "dt") var dt: Int?,

    @Json(name = "id") var id: Int?,

    @Json(name = "main") var main: Main?,

    @Json(name = "name") var name: String?,

    @Json(name = "sys") var sys: Sys?,

    @Json(name = "timezone") var timezone: Int?,

    @Json(name = "visibility") var visibility: Int?,

    @Json(name = "weather")var weather: List<Weather?>?,

    @Json(name = "wind") var wind: Wind?
)







