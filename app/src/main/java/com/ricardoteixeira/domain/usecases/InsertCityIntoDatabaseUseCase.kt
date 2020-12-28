package com.ricardoteixeira.domain.usecases

import com.ricardoteixeira.app.framework.api.mappers.toDatabase
import com.ricardoteixeira.app.framework.api.models.WeatherCityApiModel
import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.remote.repository.InsertCityIntoDatabase
import javax.inject.Inject

class InsertCityIntoDatabaseUseCase
@Inject constructor(private val insertCityIntoDatabase: InsertCityIntoDatabase):BaseUseCase<WeatherCityApiModel, Unit>{
    override suspend fun invoke(params: WeatherCityApiModel) {
        insertCityIntoDatabase.insertCityIntoDatabase(params.toDatabase())
    }
}