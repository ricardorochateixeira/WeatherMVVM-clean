package com.ricardoteixeira.domain.usecases.listcities

import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.repository.FetchCityByIdFromApi
import com.ricardoteixeira.domain.models.current.CurrentWeatherEntityModel
import com.ricardoteixeira.domain.usecases.common.BaseUseCase
import javax.inject.Inject

class FetchCurrentWeatherByIdFromApiUseCase
@Inject constructor(private var fetchNewCity: FetchCityByIdFromApi) :
    BaseUseCase<Int, Result<CurrentWeatherEntityModel>> {

    override suspend fun invoke(params: Int): Result<CurrentWeatherEntityModel> {
        return fetchNewCity.fetchWeatherByIdFromApi(params)
    }
}