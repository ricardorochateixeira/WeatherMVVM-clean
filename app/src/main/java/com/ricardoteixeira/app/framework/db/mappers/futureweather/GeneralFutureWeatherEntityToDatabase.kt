package com.ricardoteixeira.app.framework.db.mappers.futureweather

import com.ricardoteixeira.app.framework.db.model.future.DescriptionFutureWeatherDatabase
import com.ricardoteixeira.app.framework.db.model.future.GeneralFutureWeatherDatabase
import com.ricardoteixeira.domain.models.future.DescriptionFutureWeatherEntity
import com.ricardoteixeira.domain.models.future.GeneralFutureWeatherEntity

fun GeneralFutureWeatherEntity.toDatabase(): GeneralFutureWeatherDatabase {
    return GeneralFutureWeatherDatabase(
        dt = dt,
        dtTxt = dtTxt,
        mainFutureWeather = mainFutureWeather?.toDatabase(),
        descriptionFutureWeather = provideWeatherListToDatabase(descriptionFutureWeather)
    )
}

fun provideWeatherListToDatabase(descriptionFutureWeatherEntity: List<DescriptionFutureWeatherEntity?>?): List<DescriptionFutureWeatherDatabase> {
    lateinit var DescriptionFutureWeatherDatabase: List<DescriptionFutureWeatherDatabase>

    if (descriptionFutureWeatherEntity != null) {
        DescriptionFutureWeatherDatabase = descriptionFutureWeatherEntity.map { it!!.toDatabase() }
    }
    return DescriptionFutureWeatherDatabase
}