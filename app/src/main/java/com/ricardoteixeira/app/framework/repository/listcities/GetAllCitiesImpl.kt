package com.ricardoteixeira.app.framework.repository.listcities

import com.ricardoteixeira.app.framework.db.WeatherCityDao
import com.ricardoteixeira.app.framework.db.model.current.CurrentWeatherDatabaseModel
import com.ricardoteixeira.data.repository.GetAllCities
import javax.inject.Inject

class GetAllCitiesImpl
@Inject constructor(private val weatherDao: WeatherCityDao) : GetAllCities {

    override suspend fun getAllCities(): List<CurrentWeatherDatabaseModel> {
        return weatherDao.getCities()
    }
}