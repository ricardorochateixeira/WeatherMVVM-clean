package com.ricardoteixeira.app.framework.api

import com.ricardoteixeira.app.framework.api.models.futureweather.FutureWeatherApiModel
import com.ricardoteixeira.app.utils.Result
import javax.inject.Inject

class ApiHelperImpl
    @Inject constructor(private val weatherService: WeatherService): ApiHelper {

    override suspend fun getWeather(q: String) = try {
        val data = weatherService.getCurrentWeather(q)
        Result.Success(data)
    }catch (error: Throwable){
        Result.Failure(error.message!!)
    }

    override suspend fun getFutureWeather(q: String): Result<FutureWeatherApiModel>  = try {
        val data = weatherService.getFutureWeather(q)
        Result.Success(data)
    }catch (error: Throwable){
        Result.Failure(error.message!!)
    }

}

