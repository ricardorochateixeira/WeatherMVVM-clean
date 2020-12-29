package com.ricardoteixeira.app.framework.api.repositoryImpl

import com.ricardoteixeira.app.framework.api.WeatherService
import com.ricardoteixeira.app.framework.api.mappers.toDatabase
import com.ricardoteixeira.app.framework.api.models.WeatherCityApiModel
import com.ricardoteixeira.app.framework.db.WeatherCityDao
import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.remote.mappers.toEntity
import com.ricardoteixeira.data.remote.repository.FetchCityFromApi
import com.ricardoteixeira.domain.models.WeatherCityEntity
import javax.inject.Inject

class FetchCityFromApiImpl
    @Inject constructor(private val weatherCityDao: WeatherCityDao, private val weatherService: WeatherService): FetchCityFromApi {

    override suspend fun fetchWeatherFromApi(cityName: String): Result<WeatherCityEntity> =
        try {
            val city = weatherService.getCurrentWeather(cityName)
            weatherCityDao.insertCity(city.toDatabase())
            Result.Success(city.toEntity())
        } catch (error: Throwable) {
            Result.Failure(error.toString())

        }
}