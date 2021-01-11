package com.ricardoteixeira.domain.usecases.listcities

import com.ricardoteixeira.data.repository.GetCityPendingDelete
import com.ricardoteixeira.domain.models.WeatherCityEntity
import com.ricardoteixeira.domain.usecases.common.BaseUseCase
import javax.inject.Inject

class GetCityPendingDeleteUseCase
@Inject constructor(private val getCityPendingDelete: GetCityPendingDelete):
    BaseUseCase<Unit, WeatherCityEntity?> {
    override suspend fun invoke(params: Unit): WeatherCityEntity? {
        return getCityPendingDelete.getCityPendingDelete()
    }
}
