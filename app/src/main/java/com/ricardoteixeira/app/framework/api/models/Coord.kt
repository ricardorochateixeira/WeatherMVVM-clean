package com.ricardoteixeira.app.framework.api.models

import com.squareup.moshi.Json

data class Coord(

    @Json(name = "lat") var lat: Double?,

    @Json(name = "lon") var lon: Double?
)
