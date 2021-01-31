package com.ricardoteixeira.app.framework.db.mappers

import com.ricardoteixeira.app.framework.db.model.current.CurrentWeatherDatabaseModel
import com.ricardoteixeira.domain.models.current.CurrentWeatherEntityModel

fun CurrentWeatherEntityModel.toDatabase(): CurrentWeatherDatabaseModel {

    return CurrentWeatherDatabaseModel(
        cityName = cityName,
        cityId = cityId,
        actualTemp = actualTemp,
        tempMax = tempMax,
        tempMin = tempMin,
        weatherId = weatherId,
        feelsLikeTemp = feelsLikeTemp,
        weatherDescription = weatherDescription,
        isDeletePending = isUpdatePending,
        isFavorite = isFavorite,
        requestTime = requestTime,
        humidity = humidity,
        windSpeed = windSpeed,
        sunrise = sunrise,
        sunset = sunset
    )
}