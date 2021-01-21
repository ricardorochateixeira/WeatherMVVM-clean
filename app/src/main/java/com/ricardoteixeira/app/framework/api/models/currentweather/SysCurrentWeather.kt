package com.ricardoteixeira.app.framework.api.models.currentweather

import com.squareup.moshi.Json

data class SysCurrentWeather(

    @field:Json(name = "country") var country: String?,

    @field:Json(name = "sunrise") var sunrise: Int?,

    @field:Json(name = "sunset") var sunset: Int?
)
