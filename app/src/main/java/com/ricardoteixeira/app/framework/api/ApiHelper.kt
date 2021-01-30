package com.ricardoteixeira.app.framework.api

import com.ricardoteixeira.app.framework.api.models.currentweather.CurrentWeatherApiModel
import com.ricardoteixeira.app.framework.api.models.futureweather.FutureWeatherApiModel
import com.ricardoteixeira.app.utils.Result

interface ApiHelper {

    suspend fun getWeatherByName(q: String): Result<CurrentWeatherApiModel>

    suspend fun getFutureWeatherByName(q: String):Result<FutureWeatherApiModel>

    suspend fun getWeatherById(id: Int): Result<CurrentWeatherApiModel>

    suspend fun getFutureWeatherById(id: Int):Result<FutureWeatherApiModel>

}
