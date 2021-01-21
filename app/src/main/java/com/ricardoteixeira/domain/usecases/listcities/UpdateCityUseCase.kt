package com.ricardoteixeira.domain.usecases.listcities

import com.ricardoteixeira.app.framework.db.model.current.CurrentWeatherDatabaseModel
import com.ricardoteixeira.data.repository.UpdateCity
import com.ricardoteixeira.domain.usecases.common.BaseUseCase

class UpdateCityUseCase(private val updateCity: UpdateCity):
    BaseUseCase<CurrentWeatherDatabaseModel, Unit> {
    override suspend fun invoke(params: CurrentWeatherDatabaseModel) {
        return updateCity.updateCity(params)
    }
}