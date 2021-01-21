package com.ricardoteixeira.domain.usecases.listcities

import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.repository.RefreshCities
import com.ricardoteixeira.domain.models.current.CurrentWeatherEntityModel
import com.ricardoteixeira.domain.usecases.common.BaseUseCase

class RefreshCitiesUseCase(private val refreshCities: RefreshCities):
    BaseUseCase<MutableList<CurrentWeatherEntityModel>, Result<List<CurrentWeatherEntityModel?>>> {
    override suspend fun invoke(params: MutableList<CurrentWeatherEntityModel>): Result<List<CurrentWeatherEntityModel?>> {
        return refreshCities.refreshCities(params)
    }


}


