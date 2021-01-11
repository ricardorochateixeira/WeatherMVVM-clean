package com.ricardoteixeira.domain.usecases.listcities

import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.repository.FetchCityFromApi
import com.ricardoteixeira.domain.models.WeatherCityEntity
import com.ricardoteixeira.domain.usecases.common.BaseUseCase
import javax.inject.Inject

class FetchCityUseCase
    @Inject constructor(private var fetchNewCity: FetchCityFromApi) :
    BaseUseCase<String, Result<WeatherCityEntity>> {

    override suspend fun invoke(params: String): Result<WeatherCityEntity> {
        return fetchNewCity.fetchWeatherFromApi(params)
    }
}