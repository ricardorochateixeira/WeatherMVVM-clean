package com.ricardoteixeira.app.framework.db.mappers.futureweather

import com.ricardoteixeira.app.framework.db.model.future.GeneralFutureWeatherDatabase
import com.ricardoteixeira.app.framework.db.model.future.DescriptionFutureWeatherDatabase
import com.ricardoteixeira.domain.models.future.GeneralFutureWeatherEntity
import com.ricardoteixeira.domain.models.future.DescriptionFutureWeatherEntity

fun GeneralFutureWeatherDatabase.toEntity(): GeneralFutureWeatherEntity {
    return GeneralFutureWeatherEntity(
        dt = dt,
        dtTxt = dtTxt,
        mainFutureWeather = mainFutureWeather?.toEntity(),
        descriptionFutureWeather = provideWeatherListToEntity(descriptionFutureWeather)
    )
}

fun provideWeatherListToEntity (descriptionFutureWeather: List<DescriptionFutureWeatherDatabase?>?): List<DescriptionFutureWeatherEntity> {
    lateinit var descriptionFutureWeatherDatabase: List<DescriptionFutureWeatherEntity>

    if (descriptionFutureWeather != null) {
        descriptionFutureWeatherDatabase = descriptionFutureWeather.map { it!!.toEntity() }
    }
    return descriptionFutureWeatherDatabase
}