package com.ricardoteixeira.app.framework.db.mappers.futureweather

import com.ricardoteixeira.app.framework.db.model.future.GeneralFutureWeatherDatabase
import com.ricardoteixeira.app.framework.db.model.future.FutureWeatherDatabaseModel
import com.ricardoteixeira.domain.models.future.GeneralFutureWeatherEntity
import com.ricardoteixeira.domain.models.future.FutureWeatherEntityModel

fun FutureWeatherEntityModel.toDatabase(): FutureWeatherDatabaseModel {

    return FutureWeatherDatabaseModel(
        cityId = cityId,
        cityName = cityName,
        generalFutureWeather = provideFutureWeatherListToDatabase(generalFutureWeather)
    )

}

fun provideFutureWeatherListToDatabase(generalFutureWeatherEntity: List<GeneralFutureWeatherEntity>): List<GeneralFutureWeatherDatabase> {
    return generalFutureWeatherEntity.map { it.toDatabase() }
}