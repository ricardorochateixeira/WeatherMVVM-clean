package com.ricardoteixeira.app.framework.api.mappers.currentweather

import com.ricardoteixeira.app.framework.api.models.currentweather.CurrentWeatherApiModel
import com.ricardoteixeira.app.framework.db.model.current.CurrentWeatherDatabaseModel

 fun CurrentWeatherApiModel.toDatabase(): CurrentWeatherDatabaseModel {

    return CurrentWeatherDatabaseModel(
        cityId = id,
        cityName = name,
        actualTemp = main?.temp,
        feelsLikeTemp = main?.feelsLike,
        tempMin = main?.tempMin,
        tempMax = main?.tempMax,
        weatherId = descriptionCurrentWeather?.get(0)?.weatherId,
        weatherDescription = descriptionCurrentWeather?.get(0)?.description,
        requestTime = dt!!.toInt(),
        humidity = main?.humidity,
        windSpeed = windCurrentWeather?.speed,
        sunrise = sysCurrentWeather?.sunrise,
        sunset = sysCurrentWeather?.sunset

    )

}