package com.ricardoteixeira.domain.models

data class WeatherCityEntity(
    val cityName: String?,
    val cityId: Int?,
    val actualTemp: Double?,
    val feelsLikeTemp: Double?,
    val tempMin: Double?,
    val tempMax: Double?,
    val weatherDescription: String?,
    val weatherId: Int?,
    val requestTime: Int

)
