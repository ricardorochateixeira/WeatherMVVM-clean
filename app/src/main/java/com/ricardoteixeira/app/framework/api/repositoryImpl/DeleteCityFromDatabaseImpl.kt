package com.ricardoteixeira.app.framework.api.repositoryImpl

import com.ricardoteixeira.app.framework.db.WeatherCityDao
import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.remote.repository.DeleteCityFromDatabase
import javax.inject.Inject

class DeleteCityFromDatabaseImpl
    @Inject constructor(private val weatherDao: WeatherCityDao): DeleteCityFromDatabase {
    override suspend fun deleteCityFromDatabase(cityId: Int) {
        weatherDao.deleteCity(cityId)
    }
}