package com.ricardoteixeira.app.framework.repository

import com.ricardoteixeira.app.framework.db.WeatherCityDao
import com.ricardoteixeira.app.framework.db.model.WeatherCityDatabaseModel
import com.ricardoteixeira.data.repository.UpdateCity
import javax.inject.Inject

class UpdateCityImpl
@Inject constructor(private val weatherCityDao: WeatherCityDao): UpdateCity {
    override suspend fun updateCity(city: WeatherCityDatabaseModel) {
        weatherCityDao.updateCity(city)
    }

}