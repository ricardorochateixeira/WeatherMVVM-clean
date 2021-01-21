package com.ricardoteixeira.domain.usecases.details

import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.repository.GetCityById
import com.ricardoteixeira.domain.models.current.CurrentWeatherEntityModel
import com.ricardoteixeira.domain.usecases.common.BaseUseCase
import javax.inject.Inject

class GetCityByIdUseCase @Inject constructor(private val getCityById: GetCityById):BaseUseCase<Int, Result<CurrentWeatherEntityModel>>{
    override suspend fun invoke(params: Int): Result<CurrentWeatherEntityModel> {
        return getCityById.getCityById(params)
    }
}
