package com.ricardoteixeira.app.framework.db.mappers

import com.ricardoteixeira.app.framework.db.model.WeatherCityDatabaseModel
import com.ricardoteixeira.domain.models.WeatherCityEntity

inline fun WeatherCityEntity.toDatabase(): WeatherCityDatabaseModel {

    return WeatherCityDatabaseModel(
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