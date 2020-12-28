package com.ricardoteixeira.app.framework.api

import com.ricardoteixeira.app.framework.api.models.WeatherCityApiModel
import com.ricardoteixeira.app.utils.Result
import kotlinx.coroutines.flow.Flow

interface ApiHelper {

    suspend fun getWeather(q: String): Result<WeatherCityApiModel>

}
