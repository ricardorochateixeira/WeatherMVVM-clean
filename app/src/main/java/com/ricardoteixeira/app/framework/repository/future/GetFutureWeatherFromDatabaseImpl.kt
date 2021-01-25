package com.ricardoteixeira.app.framework.repository.future

import com.ricardoteixeira.app.framework.db.FutureWeatherDao
import com.ricardoteixeira.app.framework.db.WeatherCityDao
import com.ricardoteixeira.app.framework.db.mappers.futureweather.toEntity
import com.ricardoteixeira.data.repository.GetFutureWeatherFromDatabase
import com.ricardoteixeira.domain.models.future.FutureWeatherEntityModel
import javax.inject.Inject

class GetFutureWeatherFromDatabaseImpl
@Inject constructor(private val futureWeatherDao: FutureWeatherDao): GetFutureWeatherFromDatabase {
    override suspend fun getFutureWeatherFromDatabase(cityId: Int): FutureWeatherEntityModel {
        return futureWeatherDao.getCityById(cityId)!!.toEntity()
    }
}