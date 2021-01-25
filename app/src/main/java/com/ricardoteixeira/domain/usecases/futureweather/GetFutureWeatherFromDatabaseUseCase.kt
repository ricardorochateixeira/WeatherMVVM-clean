package com.ricardoteixeira.domain.usecases.futureweather

import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.repository.FetchFutureWeatherFromApi
import com.ricardoteixeira.data.repository.GetFutureWeatherFromDatabase
import com.ricardoteixeira.domain.models.future.FutureWeatherEntityModel
import com.ricardoteixeira.domain.usecases.common.BaseUseCase
import javax.inject.Inject

class GetFutureWeatherFromDatabaseUseCase
@Inject constructor(private var getFutureWeatherFromDatabase: GetFutureWeatherFromDatabase): GetFutureWeatherFromDatabase{
    override suspend fun getFutureWeatherFromDatabase(cityId: Int): FutureWeatherEntityModel {
        return getFutureWeatherFromDatabase.getFutureWeatherFromDatabase(cityId)
    }

}

