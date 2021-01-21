package com.ricardoteixeira.app.framework.repository

import com.ricardoteixeira.app.framework.db.WeatherCityDao
import com.ricardoteixeira.app.framework.db.model.current.CurrentWeatherDatabaseModel
import com.ricardoteixeira.data.repository.InsertCityIntoDatabase
import javax.inject.Inject

class InsertCityIntoDatabaseImpl
@Inject constructor(private val weatherDao: WeatherCityDao): InsertCityIntoDatabase {

    override suspend fun insertCityIntoDatabase(city: CurrentWeatherDatabaseModel) {
        weatherDao.insertCity(city)
    }
}