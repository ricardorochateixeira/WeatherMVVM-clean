package com.ricardoteixeira.domain.usecases.listcities

import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.repository.WeatherRepository
import com.ricardoteixeira.domain.models.current.CurrentWeatherEntityModel
import com.ricardoteixeira.domain.usecases.common.BaseUseCase
import javax.inject.Inject


class GetAllCitiesUseCase
    @Inject constructor(private val weatherRepository: WeatherRepository):
    BaseUseCase<Unit, Result<List<CurrentWeatherEntityModel>>> {

    override suspend fun invoke(params: Unit): Result<List<CurrentWeatherEntityModel>>  = weatherRepository.decideWhereToFetch()


}