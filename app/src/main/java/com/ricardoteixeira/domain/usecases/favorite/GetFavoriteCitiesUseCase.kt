package com.ricardoteixeira.domain.usecases.favorite

import com.ricardoteixeira.data.repository.GetFavoriteCities
import com.ricardoteixeira.domain.models.current.CurrentWeatherEntityModel
import com.ricardoteixeira.domain.usecases.common.BaseUseCase
import javax.inject.Inject

class GetFavoriteCitiesUseCase @Inject constructor(private val getFavoriteCities: GetFavoriteCities):
    BaseUseCase<Unit, List<CurrentWeatherEntityModel>> {

    override suspend fun invoke(params: Unit): List<CurrentWeatherEntityModel> = getFavoriteCities.getFavoriteCities()
}
