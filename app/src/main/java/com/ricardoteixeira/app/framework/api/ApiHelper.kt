package com.ricardoteixeira.app.framework.api

import com.ricardoteixeira.app.framework.api.models.currentweather.CurrentWeatherApiModel
import com.ricardoteixeira.app.framework.api.models.futureweather.FutureWeatherApiModel
import com.ricardoteixeira.app.utils.Result

interface ApiHelper {

    suspend fun getWeather(q: String): Result<CurrentWeatherApiModel>

    suspend fun getFutureWeather(q: String):Result<FutureWeatherApiModel>

}
