package com.ricardoteixeira.domain.usecases.listcities

import com.ricardoteixeira.app.framework.db.model.WeatherCityDatabaseModel
import com.ricardoteixeira.data.repository.UpdateCity
import com.ricardoteixeira.domain.usecases.common.BaseUseCase

class UpdateCityUseCase(private val updateCity: UpdateCity):
    BaseUseCase<WeatherCityDatabaseModel, Unit> {
    override suspend fun invoke(params: WeatherCityDatabaseModel) {
        return updateCity.updateCity(params)
    }
}