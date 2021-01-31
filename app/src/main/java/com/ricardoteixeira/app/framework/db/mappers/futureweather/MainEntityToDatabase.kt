package com.ricardoteixeira.app.framework.db.mappers.futureweather

import com.ricardoteixeira.app.framework.db.model.future.MainFutureWeatherDatabase
import com.ricardoteixeira.domain.models.future.MainFutureWeatherEntity

fun MainFutureWeatherEntity.toDatabase(): MainFutureWeatherDatabase {
    return MainFutureWeatherDatabase(
        feelsLike = feelsLike,
        temp = temp,
        tempKf = tempKf,
        tempMin = tempMin,
        tempMax = tempMax
    )
}