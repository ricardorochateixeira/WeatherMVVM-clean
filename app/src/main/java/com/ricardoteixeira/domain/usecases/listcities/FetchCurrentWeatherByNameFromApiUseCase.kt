package com.ricardoteixeira.domain.usecases.listcities

import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.repository.FetchCityByNameFromApi
import com.ricardoteixeira.domain.models.current.CurrentWeatherEntityModel
import com.ricardoteixeira.domain.usecases.common.BaseUseCase
import javax.inject.Inject

class FetchCurrentWeatherByNameFromApiUseCase
@Inject constructor(private var fetchNewCity: FetchCityByNameFromApi) :
    BaseUseCase<String, Result<CurrentWeatherEntityModel>> {

    override suspend fun invoke(params: String): Result<CurrentWeatherEntityModel> {
        return fetchNewCity.fetchWeatherByNameFromApi(params)
    }
}