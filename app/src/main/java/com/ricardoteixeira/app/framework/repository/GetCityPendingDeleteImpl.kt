package com.ricardoteixeira.app.framework.repository

import com.ricardoteixeira.app.framework.db.WeatherCityDao
import com.ricardoteixeira.app.framework.db.mappers.toEntity
import com.ricardoteixeira.data.repository.GetCityPendingDelete
import com.ricardoteixeira.domain.models.WeatherCityEntity
import javax.inject.Inject

class GetCityPendingDeleteImpl
@Inject constructor(private val weatherDao: WeatherCityDao): GetCityPendingDelete {
    override suspend fun getCityPendingDelete(): WeatherCityEntity? {
        return weatherDao.getCityPendingDelete()?.toEntity()
    }

}