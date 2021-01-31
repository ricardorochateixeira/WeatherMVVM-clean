package com.ricardoteixeira.app.framework.repository.future

import com.ricardoteixeira.app.framework.db.FutureWeatherDao
import com.ricardoteixeira.app.framework.db.WeatherCityDao
import com.ricardoteixeira.app.framework.db.model.current.CurrentWeatherDatabaseModel
import com.ricardoteixeira.app.framework.db.model.future.FutureWeatherDatabaseModel
import com.ricardoteixeira.data.repository.InsertCurrentWeatherIntoDatabase
import com.ricardoteixeira.data.repository.InsertFutureWeatherIntoDatabase
import javax.inject.Inject

class InsertFutureWeatherIntoDatabaseImpl
@Inject constructor(private val futureWeatherDao: FutureWeatherDao) :
    InsertFutureWeatherIntoDatabase {
    override suspend fun insertFutureWeatherIntoDatabase(city: FutureWeatherDatabaseModel) {
        futureWeatherDao.insertCity(city)
    }
}


class InsertCityIntoDatabaseImpl
@Inject constructor(private val weatherDao: WeatherCityDao) : InsertCurrentWeatherIntoDatabase {

    override suspend fun insertCurrentWeatherIntoDatabase(city: CurrentWeatherDatabaseModel) {
        weatherDao.insertCity(city)
    }
}