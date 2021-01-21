package com.ricardoteixeira.domain.usecases.futureweather

import com.ricardoteixeira.app.framework.db.model.future.FutureWeatherDatabaseModel
import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.repository.FetchFutureWeatherFromApi
import com.ricardoteixeira.domain.models.future.FutureWeatherEntityModel
import com.ricardoteixeira.domain.usecases.common.BaseUseCase
import javax.inject.Inject

class FetchFutureWeatherUseCase
@Inject constructor(private var fetchFutureWeather: FetchFutureWeatherFromApi) :
    BaseUseCase<String, Result<FutureWeatherEntityModel>>{
    override suspend fun invoke(params: String): Result<FutureWeatherEntityModel> {
        return fetchFutureWeather.fetchFutureWeatherFromApi(params)
    }
}
