package com.ricardoteixeira.domain.usecases.listcities

import com.ricardoteixeira.app.framework.api.mappers.toDatabase
import com.ricardoteixeira.app.framework.api.models.WeatherCityApiModel
import com.ricardoteixeira.data.repository.InsertCityIntoDatabase
import com.ricardoteixeira.domain.usecases.common.BaseUseCase
import javax.inject.Inject

class InsertCityIntoDatabaseUseCase
@Inject constructor(private val insertCityIntoDatabase: InsertCityIntoDatabase):
    BaseUseCase<WeatherCityApiModel, Unit> {
    override suspend fun invoke(params: WeatherCityApiModel) {
        insertCityIntoDatabase.insertCityIntoDatabase(params.toDatabase())
    }
}