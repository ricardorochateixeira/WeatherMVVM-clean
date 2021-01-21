package com.ricardoteixeira.app.framework.repository

import com.ricardoteixeira.app.framework.api.WeatherService
import com.ricardoteixeira.app.framework.api.mappers.currentweather.toDatabase
import com.ricardoteixeira.app.framework.db.WeatherCityDao
import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.mappers.toEntity
import com.ricardoteixeira.data.repository.FetchCityFromApi
import com.ricardoteixeira.domain.models.current.CurrentWeatherEntityModel
import javax.inject.Inject

class FetchCityFromApiImpl
    @Inject constructor(private val weatherCityDao: WeatherCityDao, private val weatherService: WeatherService): FetchCityFromApi {

    override suspend fun fetchWeatherFromApi(cityName: String): Result<CurrentWeatherEntityModel> =
        try {
            val city = weatherService.getCurrentWeather(cityName)
            weatherCityDao.insertCity(city.toDatabase())
            Result.Success(city.toEntity())
        } catch (error: Throwable) {
            Result.Failure(error.toString())

        }
}