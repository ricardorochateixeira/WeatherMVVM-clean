package com.ricardoteixeira.app.framework.repository.listcities

import com.ricardoteixeira.app.framework.db.WeatherCityDao
import com.ricardoteixeira.data.repository.DeleteCityFromDatabase
import javax.inject.Inject

class DeleteCityFromDatabaseImpl
    @Inject constructor(private val weatherDao: WeatherCityDao): DeleteCityFromDatabase {
    override suspend fun deleteCityFromDatabase(cityId: Int) {
        weatherDao.deleteCity(cityId)
    }
}