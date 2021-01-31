package com.ricardoteixeira.app.framework.repository.listcities

import com.ricardoteixeira.app.framework.api.WeatherService
import com.ricardoteixeira.app.framework.api.mappers.currentweather.toDatabase
import com.ricardoteixeira.app.framework.db.WeatherCityDao
import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.mappers.toEntity
import com.ricardoteixeira.data.repository.FetchCityByIdFromApi
import com.ricardoteixeira.domain.models.current.CurrentWeatherEntityModel
import javax.inject.Inject

class FetchCityByIdFromApiImpl
@Inject constructor(
    private val weatherCityDao: WeatherCityDao,
    private val weatherService: WeatherService
) : FetchCityByIdFromApi {

    override suspend fun fetchWeatherByIdFromApi(cityId: Int): Result<CurrentWeatherEntityModel> =
        try {
            val city = weatherService.getCurrentWeatherById(cityId)
            weatherCityDao.insertCity(city.toDatabase())
            Result.Success(city.toEntity())
        } catch (error: Throwable) {
            Result.Failure(error.toString())
        }
}





