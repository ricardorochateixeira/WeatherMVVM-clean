package com.ricardoteixeira.domain.models.future

data class FutureWeatherEntityModel(
    val cityId: Int?,
    val cityName: String?,
    val generalFutureWeather: List<GeneralFutureWeatherEntity>
)
