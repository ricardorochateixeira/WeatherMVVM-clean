package com.ricardoteixeira.app.framework.api

import com.ricardoteixeira.app.framework.db.WeatherCityDao
import com.ricardoteixeira.app.framework.db.model.WeatherCityDatabaseModel
import com.ricardoteixeira.data.repository.InsertCityIntoDatabase
import javax.inject.Inject

class InsertCityIntoDatabaseImpl
@Inject constructor(private val weatherDao: WeatherCityDao): InsertCityIntoDatabase {

    override suspend fun insertCityIntoDatabase(city: WeatherCityDatabaseModel) {
        weatherDao.insertCity(city)
    }
}