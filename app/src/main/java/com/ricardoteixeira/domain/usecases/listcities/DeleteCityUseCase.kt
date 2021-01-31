package com.ricardoteixeira.domain.usecases.listcities

import com.ricardoteixeira.data.repository.DeleteCityFromDatabase
import com.ricardoteixeira.domain.usecases.common.BaseUseCase
import javax.inject.Inject

class DeleteCityUseCase
@Inject constructor(private val deleteCityFromDatabase: DeleteCityFromDatabase) :
    BaseUseCase<Int, Unit> {

    override suspend fun invoke(params: Int) {
        return deleteCityFromDatabase.deleteCityFromDatabase(params)
    }
}