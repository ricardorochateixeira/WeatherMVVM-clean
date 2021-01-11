package com.ricardoteixeira.app.framework.db.mappers

import com.ricardoteixeira.app.framework.db.model.WeatherCityDatabaseModel
import com.ricardoteixeira.domain.models.WeatherCityEntity

 fun WeatherCityDatabaseModel.toEntity(): WeatherCityEntity{

    return WeatherCityEntity(
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