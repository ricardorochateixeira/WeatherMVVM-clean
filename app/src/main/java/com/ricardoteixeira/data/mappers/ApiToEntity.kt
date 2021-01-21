package com.ricardoteixeira.data.mappers

import com.ricardoteixeira.app.framework.api.models.currentweather.CurrentWeatherApiModel
import com.ricardoteixeira.domain.models.current.CurrentWeatherEntityModel

internal fun CurrentWeatherApiModel.toEntity(): CurrentWeatherEntityModel {
    return CurrentWeatherEntityModel(
        cityName = name,
        cityId = id,
        actualTemp = main?.temp,
        tempMax = main?.tempMax,
        tempMin = main?.tempMin,
        feelsLikeTemp = main?.feelsLike,
        weatherId = descriptionCurrentWeather?.get(0)?.weatherId,
        weatherDescription = descriptionCurrentWeather?.get(0)?.description,
        requestTime = dt!!,
        windSpeed = windCurrentWeather?.speed,
        humidity = main?.humidity,
        sunset = sysCurrentWeather?.sunset,
        sunrise = sysCurrentWeather?.sunrise
    )
}