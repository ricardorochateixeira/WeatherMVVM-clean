package com.ricardoteixeira.app.utils

import com.ricardoteixeira.app.framework.db.model.WeatherCityDatabaseModel
import com.ricardoteixeira.domain.models.WeatherCityEntity


sealed class Result<out T: Any>{

    data class Success<out T: Any>(val data: T) : Result<T>()
    data class Failure(val error: String) : Result<Nothing>()
}

