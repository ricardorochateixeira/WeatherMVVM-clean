package com.ricardoteixeira.domain.usecases.listcities

import com.ricardoteixeira.data.repository.GetCityPendingDelete
import com.ricardoteixeira.domain.models.current.CurrentWeatherEntityModel
import com.ricardoteixeira.domain.usecases.common.BaseUseCase
import javax.inject.Inject

class GetCityPendingDeleteUseCase
@Inject constructor(private val getCityPendingDelete: GetCityPendingDelete):
    BaseUseCase<Unit, CurrentWeatherEntityModel?> {
    override suspend fun invoke(params: Unit): CurrentWeatherEntityModel? {
        return getCityPendingDelete.getCityPendingDelete()
    }
}
