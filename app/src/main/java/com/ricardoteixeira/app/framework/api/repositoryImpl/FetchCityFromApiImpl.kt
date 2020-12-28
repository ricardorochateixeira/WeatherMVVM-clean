package com.ricardoteixeira.app.framework.api.repositoryImpl

import com.ricardoteixeira.app.framework.api.WeatherService
import com.ricardoteixeira.app.framework.api.models.WeatherCityApiModel
import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.remote.repository.FetchCityFromApi
import javax.inject.Inject

class FetchCityFromApiImpl
    @Inject constructor(private val weatherService: WeatherService): FetchCityFromApi {

    override suspend fun fetchWeatherFromApi(cityName: String): Result<WeatherCityApiModel> =
        try {
            Result.Success(weatherService.getCurrentWeather(cityName))
        } catch (error: Throwable) {
            Result.Failure(error.toString())

        }
}