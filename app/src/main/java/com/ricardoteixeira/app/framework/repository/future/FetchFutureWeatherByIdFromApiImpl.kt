package com.ricardoteixeira.app.framework.repository.future

import com.ricardoteixeira.app.framework.api.WeatherService
import com.ricardoteixeira.app.framework.api.mappers.toDatabase
import com.ricardoteixeira.app.framework.db.FutureWeatherDao
import com.ricardoteixeira.app.framework.db.mappers.futureweather.toEntity
import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.repository.FetchFutureWeatherByIdFromApi
import com.ricardoteixeira.data.repository.FetchFutureWeatherByNameFromApi
import com.ricardoteixeira.domain.models.future.FutureWeatherEntityModel
import javax.inject.Inject

class FetchFutureWeatherByIdFromApiImpl @Inject constructor(private val futureWeatherDao: FutureWeatherDao, private val weatherService: WeatherService):
    FetchFutureWeatherByIdFromApi {

    override suspend fun fetchFutureWeatherByIdFromApi(cityId: Int): Result<FutureWeatherEntityModel> =
    try {
        val city = weatherService.getFutureWeatherById(cityId)
        futureWeatherDao.insertCity(city.toDatabase())
        Result.Success(city.toDatabase().toEntity())
    } catch (error: Throwable) {
        Result.Failure(error.toString())
    }
}