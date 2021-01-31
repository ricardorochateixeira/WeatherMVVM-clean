package com.ricardoteixeira.domain.usecases.futureweather

import com.ricardoteixeira.data.repository.GetFutureWeatherFromDatabase
import com.ricardoteixeira.domain.models.future.FutureWeatherEntityModel
import javax.inject.Inject

class GetFutureWeatherFromDatabaseUseCase
@Inject constructor(private var getFutureWeatherFromDatabase: GetFutureWeatherFromDatabase) :
    GetFutureWeatherFromDatabase {
    override suspend fun getFutureWeatherFromDatabase(cityId: Int): FutureWeatherEntityModel {
        return getFutureWeatherFromDatabase.getFutureWeatherFromDatabase(cityId)
    }
}

