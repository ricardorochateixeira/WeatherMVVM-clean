package com.ricardoteixeira.app.framework.db.mappers.futureweather

import com.ricardoteixeira.app.framework.db.model.future.FutureWeatherDatabaseModel
import com.ricardoteixeira.app.framework.db.model.future.GeneralFutureWeatherDatabase
import com.ricardoteixeira.domain.models.future.FutureWeatherEntityModel
import com.ricardoteixeira.domain.models.future.GeneralFutureWeatherEntity

fun FutureWeatherDatabaseModel.toEntity(): FutureWeatherEntityModel {

    return FutureWeatherEntityModel(
        cityId = cityId,
        cityName = cityName,
        generalFutureWeather = provideFutureWeatherListToEntity(generalFutureWeather)
    )

}

fun provideFutureWeatherListToEntity(generalFutureWeatherDatabase: List<GeneralFutureWeatherDatabase>): List<GeneralFutureWeatherEntity> {
    return generalFutureWeatherDatabase.map { it.toEntity() }
}