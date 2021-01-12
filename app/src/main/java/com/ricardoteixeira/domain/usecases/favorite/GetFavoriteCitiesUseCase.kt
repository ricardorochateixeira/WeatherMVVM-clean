package com.ricardoteixeira.domain.usecases.favorite

import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.repository.GetFavoriteCities
import com.ricardoteixeira.data.repository.WeatherRepository
import com.ricardoteixeira.domain.models.WeatherCityEntity
import com.ricardoteixeira.domain.usecases.common.BaseUseCase
import javax.inject.Inject

class GetFavoriteCitiesUseCase @Inject constructor(private val getFavoriteCities: GetFavoriteCities):
    BaseUseCase<Unit, List<WeatherCityEntity>> {

    override suspend fun invoke(params: Unit): List<WeatherCityEntity> = getFavoriteCities.getFavoriteCities()
}
