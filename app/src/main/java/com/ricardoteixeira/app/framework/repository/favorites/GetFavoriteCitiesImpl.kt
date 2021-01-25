package com.ricardoteixeira.app.framework.repository.favorites

import com.ricardoteixeira.app.framework.db.WeatherCityDao
import com.ricardoteixeira.app.framework.db.mappers.toEntity
import com.ricardoteixeira.data.repository.GetFavoriteCities
import com.ricardoteixeira.domain.models.current.CurrentWeatherEntityModel
import javax.inject.Inject

class GetFavoriteCitiesImpl @Inject constructor(private val weatherDao: WeatherCityDao): GetFavoriteCities {
    override suspend fun getFavoriteCities(): List<CurrentWeatherEntityModel> {
        val result = weatherDao.getFavoriteCities()
        if (result.isEmpty()) {
            return emptyList()
        } else {
            return result.map { it!!.toEntity() }
        }
    }
}
