package com.ricardoteixeira.app.framework.api.mappers.futureweather

import com.ricardoteixeira.app.framework.api.models.futureweather.MainFutureWeatherApi
import com.ricardoteixeira.app.framework.db.model.future.MainFutureWeatherDatabase

fun MainFutureWeatherApi.toDatabase(): MainFutureWeatherDatabase {

    return MainFutureWeatherDatabase(
        feelsLike = feelsLike,
        temp = temp,
        tempKf = tempKf,
        tempMax = tempMax,
        tempMin = tempMin
    )

}