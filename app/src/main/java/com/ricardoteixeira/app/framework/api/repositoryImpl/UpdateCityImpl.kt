package com.ricardoteixeira.app.framework.api.repositoryImpl

import com.ricardoteixeira.app.framework.db.WeatherCityDao
import com.ricardoteixeira.app.framework.db.model.WeatherCityDatabaseModel
import com.ricardoteixeira.data.remote.repository.UpdateCity
import javax.inject.Inject

class UpdateCityImpl
@Inject constructor(private val weatherCityDao: WeatherCityDao): UpdateCity {
    override suspend fun updateCity(city: WeatherCityDatabaseModel) {
        weatherCityDao.updateCity(city)
    }

}