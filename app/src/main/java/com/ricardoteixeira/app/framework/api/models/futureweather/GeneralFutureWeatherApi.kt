package com.ricardoteixeira.app.framework.api.models.futureweather

import com.squareup.moshi.Json

data class GeneralFutureWeatherApi(
    @Json(name = "clouds")
    var cloudsFutureWeatherApi: CloudsFutureWeatherApi?,
    @Json(name = "dt")
    var dt: Int?,
    @Json(name = "dt_txt")
    var dtTxt: String?,
    @Json(name = "main")
    var mainFutureWeatherApi: MainFutureWeatherApi?,
    @Json(name = "sys")
    var sysFutureWeatherApi: SysFutureWeatherApi?,
    @Json(name = "visibility")
    var visibility: Int?,
    @Json(name = "weather")
    var descriptionFutureWeatherApi: List<DescriptionFutureWeatherApi?>?,
    @Json(name = "wind")
    var windFutureWeatherApi: WindFutureWeatherApi?
)
