package com.ricardoteixeira.domain.usecases

import com.ricardoteixeira.app.framework.db.model.WeatherCityDatabaseModel
import com.ricardoteixeira.data.remote.repository.DeleteCityFromDatabase
import com.ricardoteixeira.data.remote.repository.UpdateCity
import javax.inject.Inject

class UpdateCityUseCase(private val updateCity: UpdateCity): BaseUseCase<WeatherCityDatabaseModel, Unit> {
    override suspend fun invoke(params: WeatherCityDatabaseModel) {
        return updateCity.updateCity(params)
    }
}