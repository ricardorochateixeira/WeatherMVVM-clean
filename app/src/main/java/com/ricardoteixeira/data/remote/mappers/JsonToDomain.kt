package com.ricardoteixeira.data.remote.mappers

import com.ricardoteixeira.app.framework.api.models.WeatherCityApiModel
import com.ricardoteixeira.domain.models.WeatherCityEntity

internal fun WeatherCityApiModel.toEntity(): WeatherCityEntity {
    return WeatherCityEntity(
        cityName = name,
        cityId = id,
        actualTemp = main?.temp,
        tempMax = main?.tempMax,
        tempMin = main?.tempMin,
        feelsLikeTemp = main?.feelsLike,
        weatherId = weather?.get(0)?.weatherId,
        weatherDescription = weather?.get(0)?.description,
        requestTime = dt!!
    )
}