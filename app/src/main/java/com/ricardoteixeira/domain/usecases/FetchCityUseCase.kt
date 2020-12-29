package com.ricardoteixeira.domain.usecases

import com.ricardoteixeira.app.framework.api.models.WeatherCityApiModel
import com.ricardoteixeira.app.framework.db.WeatherCityDao
import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.remote.repository.FetchCityFromApi
import com.ricardoteixeira.data.remote.repository.InsertCityIntoDatabase
import com.ricardoteixeira.domain.models.WeatherCityEntity
import javax.inject.Inject

class FetchCityUseCase
    @Inject constructor(private var fetchNewCity: FetchCityFromApi) :BaseUseCase<String, Result<WeatherCityEntity>> {

    override suspend fun invoke(params: String): Result<WeatherCityEntity> {
        return fetchNewCity.fetchWeatherFromApi(params)
    }
}