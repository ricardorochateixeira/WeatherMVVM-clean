package com.ricardoteixeira.app.framework.api.mappers

import com.ricardoteixeira.app.framework.api.models.WeatherCityApiModel
import com.ricardoteixeira.app.framework.db.model.WeatherCityDatabaseModel

inline fun WeatherCityApiModel.toDatabase(): WeatherCityDatabaseModel {

    return WeatherCityDatabaseModel(
        cityId = id,
        cityName = name,
        actualTemp = main?.temp,
        feelsLikeTemp = main?.feelsLike,
        tempMin = main?.tempMin,
        tempMax = main?.tempMax,
        weatherId = weather?.get(0)?.weatherId,
        weatherDescription = weather?.get(0)?.description,
        requestTime = dt!!.toInt()
    )

}