package com.ricardoteixeira.app.framework.api.mappers.futureweather

import com.ricardoteixeira.app.framework.api.models.futureweather.DescriptionFutureWeatherApi
import com.ricardoteixeira.app.framework.db.model.future.DescriptionFutureWeatherDatabase

fun DescriptionFutureWeatherApi.toDatabase(): DescriptionFutureWeatherDatabase {

    return DescriptionFutureWeatherDatabase(
        id= id,
        description = description,
        main = main
    )

}