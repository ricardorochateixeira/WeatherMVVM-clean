package com.ricardoteixeira.app.framework.repository.listcities

import com.ricardoteixeira.app.framework.db.WeatherCityDao
import com.ricardoteixeira.app.framework.db.model.current.CurrentWeatherDatabaseModel
import com.ricardoteixeira.data.repository.InsertCurrentWeatherIntoDatabase
import javax.inject.Inject

class InsertCurrentWeatherIntoDatabaseImpl
@Inject constructor(private val weatherDao: WeatherCityDao): InsertCurrentWeatherIntoDatabase {

    override suspend fun insertCurrentWeatherIntoDatabase(city: CurrentWeatherDatabaseModel) {
        weatherDao.insertCity(city)
    }
}