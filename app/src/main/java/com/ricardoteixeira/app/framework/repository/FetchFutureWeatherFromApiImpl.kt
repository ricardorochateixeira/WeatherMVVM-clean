package com.ricardoteixeira.app.framework.repository

import com.ricardoteixeira.app.framework.api.WeatherService
import com.ricardoteixeira.app.framework.api.mappers.toDatabase
import com.ricardoteixeira.app.framework.db.FutureWeatherDao
import com.ricardoteixeira.app.framework.db.mappers.futureweather.toEntity
import com.ricardoteixeira.app.framework.db.model.future.FutureWeatherDatabaseModel
import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.repository.FetchFutureWeatherFromApi
import com.ricardoteixeira.domain.models.future.FutureWeatherEntityModel
import javax.inject.Inject

class FetchFutureWeatherFromApiImpl
@Inject constructor(private val futureWeatherDao: FutureWeatherDao, private val weatherService: WeatherService):
    FetchFutureWeatherFromApi {
    override suspend fun fetchFutureWeatherFromApi(cityName: String): Result<FutureWeatherEntityModel> =
        try {
            val city = weatherService.getFutureWeather(cityName)
            futureWeatherDao.insertCity(city.toDatabase())
            Result.Success(city.toDatabase().toEntity())
        } catch (error: Throwable) {
            Result.Failure(error.toString())
        }
}