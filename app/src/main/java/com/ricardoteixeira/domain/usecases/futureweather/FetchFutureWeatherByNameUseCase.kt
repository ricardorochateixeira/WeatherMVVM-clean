package com.ricardoteixeira.domain.usecases.futureweather

import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.repository.FetchFutureWeatherByNameFromApi
import com.ricardoteixeira.domain.models.future.FutureWeatherEntityModel
import com.ricardoteixeira.domain.usecases.common.BaseUseCase
import javax.inject.Inject

class FetchFutureWeatherByNameUseCase
@Inject constructor(private var fetchFutureWeather: FetchFutureWeatherByNameFromApi) :
    BaseUseCase<String, Result<FutureWeatherEntityModel>> {

    override suspend fun invoke(params: String): Result<FutureWeatherEntityModel> {
        return fetchFutureWeather.fetchFutureWeatherByNameFromApi(params)
    }
}
