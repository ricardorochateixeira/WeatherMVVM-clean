package com.ricardoteixeira.app.framework.repository.listcities

import com.ricardoteixeira.app.framework.db.CityDao
import com.ricardoteixeira.app.framework.db.model.city.CityDatabaseModel
import com.ricardoteixeira.data.repository.GetCitiesByName
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCitiesByNameImpl
@Inject constructor(private val cityDao: CityDao) : GetCitiesByName {
    override fun getCitiesByName(searchQuery: String): Flow<List<CityDatabaseModel>> {
        return cityDao.getCitiesByName(searchQuery)
    }
}