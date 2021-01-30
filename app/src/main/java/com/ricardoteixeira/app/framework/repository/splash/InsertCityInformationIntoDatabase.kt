package com.ricardoteixeira.app.framework.repository.splash

import com.ricardoteixeira.app.framework.db.CityDao
import com.ricardoteixeira.app.framework.db.WeatherCityDao
import com.ricardoteixeira.app.framework.db.model.city.CityDatabaseModel
import com.ricardoteixeira.app.framework.db.model.current.CurrentWeatherDatabaseModel
import com.ricardoteixeira.data.repository.InsertCityInformationIntoDatabase
import com.ricardoteixeira.data.repository.InsertCityIntoDatabase
import javax.inject.Inject

class InsertCityInformationIntoDatabaseImpl
@Inject constructor(private val cityDao: CityDao): InsertCityInformationIntoDatabase {
    override suspend fun insertCityInformationIntoDatabase(city: CityDatabaseModel) {
        cityDao.insertCity(city)
    }
}
