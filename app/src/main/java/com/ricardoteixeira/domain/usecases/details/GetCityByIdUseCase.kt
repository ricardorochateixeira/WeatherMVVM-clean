package com.ricardoteixeira.domain.usecases.details

import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.repository.DeleteCityFromDatabase
import com.ricardoteixeira.data.repository.GetCityById
import com.ricardoteixeira.domain.models.WeatherCityEntity
import com.ricardoteixeira.domain.usecases.common.BaseUseCase
import javax.inject.Inject

class GetCityByIdUseCase @Inject constructor(private val getCityById: GetCityById):BaseUseCase<Int, Result<WeatherCityEntity>>{
    override suspend fun invoke(params: Int): Result<WeatherCityEntity> {
        return getCityById.getCityById(params)
    }
}
