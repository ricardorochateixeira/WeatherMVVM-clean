package com.ricardoteixeira.domain.usecases.splash

import com.ricardoteixeira.app.framework.api.models.currentweather.CurrentWeatherApiModel
import com.ricardoteixeira.app.framework.db.model.city.CityDatabaseModel
import com.ricardoteixeira.data.repository.InsertCityInformationIntoDatabase
import com.ricardoteixeira.domain.usecases.common.BaseUseCase
import javax.inject.Inject

class InsertCityInformationIntoDatabaseUseCase
@Inject constructor(private val insertCityInformationIntoDatabase: InsertCityInformationIntoDatabase): BaseUseCase<CityDatabaseModel, Unit> {
    override suspend fun invoke(params: CityDatabaseModel) {
        insertCityInformationIntoDatabase.insertCityInformationIntoDatabase(params)
    }
}
