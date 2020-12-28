package com.ricardoteixeira.app.framework.api.repositoryImpl

import com.ricardoteixeira.app.framework.db.WeatherCityDao
import com.ricardoteixeira.app.framework.db.model.WeatherCityDatabaseModel
import com.ricardoteixeira.data.remote.repository.GetAllCities
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCitiesImpl
    @Inject constructor(private val weatherDao: WeatherCityDao): GetAllCities {

    override suspend fun getCurrentListOfCities(): Flow<List<WeatherCityDatabaseModel>> {
        return weatherDao.getAllCities()
    }
}