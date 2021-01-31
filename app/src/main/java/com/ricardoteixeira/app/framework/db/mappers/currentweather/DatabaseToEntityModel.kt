package com.ricardoteixeira.app.framework.db.mappers

import com.ricardoteixeira.app.framework.db.model.current.CurrentWeatherDatabaseModel
import com.ricardoteixeira.domain.models.current.CurrentWeatherEntityModel

fun CurrentWeatherDatabaseModel.toEntity(): CurrentWeatherEntityModel {

    return CurrentWeatherEntityModel(
        cityName = cityName,
        cityId = cityId,
        actualTemp = actualTemp,
        tempMax = tempMax,
        tempMin = tempMin,
        feelsLikeTemp = feelsLikeTemp,
        weatherId = weatherId,
        weatherDescription = weatherDescription,
        isUpdatePending = isDeletePending,
        isFavorite = isFavorite,
        requestTime = requestTime,
        humidity = humidity,
        windSpeed = windSpeed,
        sunrise = sunrise,
        sunset = sunset
    )
}
