package com.ricardoteixeira.app.framework.api.models.currentweather

import com.squareup.moshi.Json

data class CoordCurrentWeather(

    @Json(name = "lat") var lat: Double?,

    @Json(name = "lon") var lon: Double?
)
