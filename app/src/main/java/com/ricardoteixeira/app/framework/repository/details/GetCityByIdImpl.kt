package com.ricardoteixeira.app.framework.repository.details

import com.ricardoteixeira.app.framework.db.WeatherCityDao
import com.ricardoteixeira.app.framework.db.mappers.toEntity
import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.repository.GetCityById
import com.ricardoteixeira.domain.models.current.CurrentWeatherEntityModel
import javax.inject.Inject

class GetCityByIdImpl @Inject constructor(private val weatherCityDao: WeatherCityDao): GetCityById {
    override suspend fun getCityById(cityId: Int): Result<CurrentWeatherEntityModel> {
        return  weatherCityDao.getCityById(cityId)?.let { Result.Success(data = it.toEntity()) } ?: Result.Failure(error = "Error")

    }

}