package com.ricardoteixeira.domain.usecases.listcities

import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.repository.RefreshCities
import com.ricardoteixeira.domain.models.WeatherCityEntity
import com.ricardoteixeira.domain.usecases.common.BaseUseCase

class RefreshCitiesUseCase(private val refreshCities: RefreshCities):
    BaseUseCase<MutableList<WeatherCityEntity>, Result<List<WeatherCityEntity?>>> {
    override suspend fun invoke(params: MutableList<WeatherCityEntity>): Result<List<WeatherCityEntity?>> {
        return refreshCities.refreshCities(params)
    }


}


