package com.ricardoteixeira.app.framework.repository

import com.ricardoteixeira.app.framework.db.WeatherCityDao
import com.ricardoteixeira.app.framework.db.mappers.toEntity
import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.repository.GetFavoriteCities
import com.ricardoteixeira.domain.models.WeatherCityEntity
import javax.inject.Inject

class GetFavoriteCitiesImpl @Inject constructor(private val weatherDao: WeatherCityDao): GetFavoriteCities {
    override suspend fun getFavoriteCities(): List<WeatherCityEntity> {
        val result = weatherDao.getFavoriteCities()
        if (result.isEmpty()) {
            return emptyList()
        } else {
            return result.map { it!!.toEntity() }
        }
    }
}
