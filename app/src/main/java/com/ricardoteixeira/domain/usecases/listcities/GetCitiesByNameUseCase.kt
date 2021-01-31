package com.ricardoteixeira.domain.usecases.listcities

import com.ricardoteixeira.app.framework.db.model.city.CityDatabaseModel
import com.ricardoteixeira.data.repository.GetCitiesByName
import com.ricardoteixeira.domain.usecases.common.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCitiesByNameUseCase
@Inject constructor(private val getCitiesByName: GetCitiesByName) :
    BaseUseCase<String, Flow<List<CityDatabaseModel>>> {

    override suspend fun invoke(params: String): Flow<List<CityDatabaseModel>> {
        return getCitiesByName.getCitiesByName(params)
    }
}

