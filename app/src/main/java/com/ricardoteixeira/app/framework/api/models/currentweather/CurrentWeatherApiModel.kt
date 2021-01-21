package com.ricardoteixeira.app.framework.api.models.currentweather

import com.squareup.moshi.Json

data class CurrentWeatherApiModel(

    @Json(name = "cod") var cod: Int?,

    @Json(name = "coord") var coord: CoordCurrentWeather?,

    @Json(name = "dt") var dt: Int?,

    @Json(name = "id") var id: Int?,

    @Json(name = "main") var main: MainCurrentWeather?,

    @Json(name = "name") var name: String?,

    @Json(name = "sys") var sysCurrentWeather: SysCurrentWeather?,

    @Json(name = "timezone") var timezone: Int?,

    @Json(name = "visibility") var visibility: Int?,

    @Json(name = "weather")var descriptionCurrentWeather: List<DescriptionCurrentWeather?>?,

    @Json(name = "wind") var windCurrentWeather: WindCurrentWeather?
)







