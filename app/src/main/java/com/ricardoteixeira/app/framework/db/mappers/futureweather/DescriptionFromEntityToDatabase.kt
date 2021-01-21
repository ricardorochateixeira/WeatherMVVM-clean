package com.ricardoteixeira.app.framework.db.mappers.futureweather

import com.ricardoteixeira.app.framework.db.model.future.DescriptionFutureWeatherDatabase
import com.ricardoteixeira.domain.models.future.DescriptionFutureWeatherEntity

fun DescriptionFutureWeatherEntity.toDatabase(): DescriptionFutureWeatherDatabase {

    return DescriptionFutureWeatherDatabase(
        description = description,
        main = main
    )
}