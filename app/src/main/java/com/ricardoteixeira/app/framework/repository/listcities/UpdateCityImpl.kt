package com.ricardoteixeira.app.framework.repository.listcities

import com.ricardoteixeira.app.framework.db.WeatherCityDao
import com.ricardoteixeira.app.framework.db.model.current.CurrentWeatherDatabaseModel
import com.ricardoteixeira.data.repository.UpdateCity
import javax.inject.Inject

class UpdateCityImpl
@Inject constructor(private val weatherCityDao: WeatherCityDao): UpdateCity {
    override suspend fun updateCity(city: CurrentWeatherDatabaseModel) {
        weatherCityDao.updateCity(city)
    }

}