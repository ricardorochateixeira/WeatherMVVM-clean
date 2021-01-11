package com.ricardoteixeira.app.framework.repository

import com.ricardoteixeira.app.framework.db.WeatherCityDao
import com.ricardoteixeira.app.framework.db.model.WeatherCityDatabaseModel
import com.ricardoteixeira.data.repository.GetAllCities
import javax.inject.Inject

class GetAllCitiesImpl
    @Inject constructor(private val weatherDao: WeatherCityDao): GetAllCities {

    override suspend fun getAllCities(): List<WeatherCityDatabaseModel> {
        return weatherDao.getCities()
    }

}