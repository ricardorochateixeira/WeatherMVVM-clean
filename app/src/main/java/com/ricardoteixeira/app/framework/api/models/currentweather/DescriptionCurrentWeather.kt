package com.ricardoteixeira.app.framework.api.models.currentweather

import com.squareup.moshi.Json

data class DescriptionCurrentWeather(

    @Json(name = "description") var description: String?,

    @Json(name = "icon") var icon: String?,

    @Json(name = "id") var weatherId: Int?,

    @Json(name = "main") var main: String?
)
