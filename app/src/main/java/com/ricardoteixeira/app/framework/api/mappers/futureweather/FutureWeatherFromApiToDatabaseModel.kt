package com.ricardoteixeira.app.framework.api.mappers

import com.ricardoteixeira.app.framework.api.mappers.futureweather.toDatabase
import com.ricardoteixeira.app.framework.api.models.futureweather.GeneralFutureWeatherApi
import com.ricardoteixeira.app.framework.api.models.futureweather.FutureWeatherApiModel
import com.ricardoteixeira.app.framework.db.model.future.GeneralFutureWeatherDatabase
import com.ricardoteixeira.app.framework.db.model.future.FutureWeatherDatabaseModel

fun FutureWeatherApiModel.toDatabase(): FutureWeatherDatabaseModel {
    return FutureWeatherDatabaseModel(
        cityId = cityFutureWeatherApi?.id,
        cityName = cityFutureWeatherApi?.name,
        generalFutureWeather = provideFutureWeatherList(generalFutureWeatherApiApiModel = list)
    )

}

fun provideFutureWeatherList(generalFutureWeatherApiApiModel: List<GeneralFutureWeatherApi?>?): List<GeneralFutureWeatherDatabase> {
    lateinit var generalFutureWeather: List<GeneralFutureWeatherDatabase>
    if (generalFutureWeatherApiApiModel != null) {
        generalFutureWeather = generalFutureWeatherApiApiModel.map { it!!.toDatabase() }
    }
    return generalFutureWeather
}
