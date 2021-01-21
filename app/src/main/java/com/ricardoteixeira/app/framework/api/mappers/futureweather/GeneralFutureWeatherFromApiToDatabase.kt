package com.ricardoteixeira.app.framework.api.mappers.futureweather

import com.ricardoteixeira.app.framework.api.models.futureweather.GeneralFutureWeatherApi
import com.ricardoteixeira.app.framework.api.models.futureweather.DescriptionFutureWeatherApi
import com.ricardoteixeira.app.framework.db.model.future.GeneralFutureWeatherDatabase
import com.ricardoteixeira.app.framework.db.model.future.DescriptionFutureWeatherDatabase

fun GeneralFutureWeatherApi.toDatabase(): GeneralFutureWeatherDatabase {

    return GeneralFutureWeatherDatabase(
        dt = dt,
        dtTxt = dtTxt,
        mainFutureWeather = mainFutureWeatherApi?.toDatabase(),
        descriptionFutureWeather = provideWeatherList(descriptionFutureWeatherApi)
    )

}

fun provideWeatherList (descriptionFutureWeatherApi: List<DescriptionFutureWeatherApi?>?): List<DescriptionFutureWeatherDatabase> {
    lateinit var descriptionFutureWeatherDatabase: List<DescriptionFutureWeatherDatabase>

    if (descriptionFutureWeatherApi != null) {
        descriptionFutureWeatherDatabase = descriptionFutureWeatherApi.map { it!!.toDatabase() }
    }
    return descriptionFutureWeatherDatabase
}


