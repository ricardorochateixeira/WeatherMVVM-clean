package com.ricardoteixeira.app.framework.api

import com.ricardoteixeira.app.framework.api.models.futureweather.FutureWeatherApiModel
import com.ricardoteixeira.app.utils.Result
import javax.inject.Inject

class ApiHelperImpl
    @Inject constructor(private val weatherService: WeatherService): ApiHelper {

    override suspend fun getWeatherByName(q: String) = try {
        val data = weatherService.getCurrentWeatherByName(q)
        Result.Success(data)
    }catch (error: Throwable){
        Result.Failure(error.message!!)
    }

    override suspend fun getFutureWeatherByName(q: String): Result<FutureWeatherApiModel>  = try {
        val data = weatherService.getFutureWeatherByName(q)
        Result.Success(data)
    }catch (error: Throwable){
        Result.Failure(error.message!!)
    }

    override suspend fun getWeatherById(id: Int) = try {
        val data = weatherService.getCurrentWeatherById(id)
        Result.Success(data)
    }catch (error: Throwable){
        Result.Failure(error.message!!)
    }

    override suspend fun getFutureWeatherById(id: Int): Result<FutureWeatherApiModel>  = try {
        val data = weatherService.getFutureWeatherById(id)
        Result.Success(data)
    }catch (error: Throwable){
        Result.Failure(error.message!!)
    }

}

