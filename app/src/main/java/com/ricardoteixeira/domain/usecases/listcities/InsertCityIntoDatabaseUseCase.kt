package com.ricardoteixeira.domain.usecases.listcities

import com.ricardoteixeira.app.framework.api.mappers.currentweather.toDatabase
import com.ricardoteixeira.app.framework.api.models.currentweather.CurrentWeatherApiModel
import com.ricardoteixeira.data.repository.InsertCurrentWeatherIntoDatabase
import com.ricardoteixeira.domain.usecases.common.BaseUseCase
import javax.inject.Inject

class InsertCityIntoDatabaseUseCase
@Inject constructor(private val insertCityIntoDatabase: InsertCurrentWeatherIntoDatabase):
    BaseUseCase<CurrentWeatherApiModel, Unit> {
    override suspend fun invoke(params: CurrentWeatherApiModel) {
        insertCityIntoDatabase.insertCurrentWeatherIntoDatabase(params.toDatabase())
    }
}