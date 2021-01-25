package com.ricardoteixeira.app.framework.db.mappers.futureweather

import com.ricardoteixeira.app.framework.db.model.future.DescriptionFutureWeatherDatabase
import com.ricardoteixeira.domain.models.future.DescriptionFutureWeatherEntity

fun DescriptionFutureWeatherDatabase.toEntity(): DescriptionFutureWeatherEntity {

    return DescriptionFutureWeatherEntity(
        id = id,
        description = description,
        main = main
    )
}