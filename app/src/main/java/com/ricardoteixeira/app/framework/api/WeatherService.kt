package com.ricardoteixeira.app.framework.api

import com.ricardoteixeira.app.framework.api.models.currentweather.CurrentWeatherApiModel
import com.ricardoteixeira.app.framework.api.models.futureweather.FutureWeatherApiModel
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(@Query("q") q: String,
    @Query("units") units: String = "metric"): CurrentWeatherApiModel

    @GET("data/2.5/forecast")
    suspend fun getFutureWeather(@Query("q") q: String,
     @Query("units") units: String = "metric"): FutureWeatherApiModel
}
