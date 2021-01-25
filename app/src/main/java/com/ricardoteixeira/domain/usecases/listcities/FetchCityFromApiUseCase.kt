package com.ricardoteixeira.domain.usecases.listcities

import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.repository.FetchCityFromApi
import com.ricardoteixeira.domain.models.current.CurrentWeatherEntityModel
import com.ricardoteixeira.domain.usecases.common.BaseUseCase
import javax.inject.Inject

class FetchCityFromApiUseCase
    @Inject constructor(private var fetchNewCity: FetchCityFromApi) :
    BaseUseCase<String, Result<CurrentWeatherEntityModel>> {

    override suspend fun invoke(params: String): Result<CurrentWeatherEntityModel> {
        return fetchNewCity.fetchWeatherFromApi(params)
    }
}