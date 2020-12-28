package com.ricardoteixeira.domain.usecases

import com.ricardoteixeira.app.framework.db.model.WeatherCityDatabaseModel
import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.remote.repository.GetAllCities
import com.ricardoteixeira.data.remote.repository.WeatherRepository
import com.ricardoteixeira.domain.models.WeatherCityEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetAllCitiesUseCase
    @Inject constructor(private val weatherRepository: WeatherRepository):BaseUseCase<Unit, Flow<Result<List<WeatherCityDatabaseModel>>>>  {

    override suspend fun invoke(params: Unit): Flow<Result<List<WeatherCityDatabaseModel>>>  = weatherRepository.decideWhereToFetch()


}