package com.ricardoteixeira.domain.usecases.futureweather

import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.repository.FetchFutureWeatherByIdFromApi
import com.ricardoteixeira.data.repository.FetchFutureWeatherByNameFromApi
import com.ricardoteixeira.domain.models.future.FutureWeatherEntityModel
import com.ricardoteixeira.domain.usecases.common.BaseUseCase
import javax.inject.Inject

class FetchFutureWeatherByIdUseCase @Inject constructor(private var fetchFutureWeather: FetchFutureWeatherByIdFromApi) :
    BaseUseCase<Int, Result<FutureWeatherEntityModel>> {
    override suspend fun invoke(params: Int): Result<FutureWeatherEntityModel> {
        return fetchFutureWeather.fetchFutureWeatherByIdFromApi(params)
    }
}