package com.ricardoteixeira.app.framework.repository.listcities

import com.ricardoteixeira.app.framework.api.WeatherService
import com.ricardoteixeira.app.framework.api.mappers.currentweather.toDatabase
import com.ricardoteixeira.app.framework.db.WeatherCityDao
import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.mappers.toEntity
import com.ricardoteixeira.data.repository.FetchCityByNameFromApi
import com.ricardoteixeira.domain.models.current.CurrentWeatherEntityModel
import javax.inject.Inject

class FetchCityByNameFromApiImpl
@Inject constructor(
    private val weatherCityDao: WeatherCityDao,
    private val weatherService: WeatherService
) : FetchCityByNameFromApi {

    override suspend fun fetchWeatherByNameFromApi(cityName: String): Result<CurrentWeatherEntityModel> =
        try {
            val city = weatherService.getCurrentWeatherByName(cityName)
            weatherCityDao.insertCity(city.toDatabase())
            Result.Success(city.toEntity())
        } catch (error: Throwable) {
            Result.Failure(error.toString())
        }
}