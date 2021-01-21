package com.ricardoteixeira.domain.models.current


data class CurrentWeatherEntityModel(
    val cityName: String?,
    val cityId: Int?,
    val actualTemp: Double?,
    val feelsLikeTemp: Double?,
    val tempMin: Double?,
    val tempMax: Double?,
    val weatherDescription: String?,
    val weatherId: Int?,
    var isUpdatePending: Boolean = false,
    var isFavorite: Boolean = false,
    val requestTime: Int,
    val humidity: Int?,
    val windSpeed: Double?,
    val sunset: Int?,
    val sunrise: Int?
)




