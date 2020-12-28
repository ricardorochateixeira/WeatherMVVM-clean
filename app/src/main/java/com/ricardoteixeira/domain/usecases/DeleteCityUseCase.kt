package com.ricardoteixeira.domain.usecases

import com.ricardoteixeira.data.remote.repository.DeleteCityFromDatabase
import javax.inject.Inject

class DeleteCityUseCase
@Inject constructor(private val deleteCityFromDatabase: DeleteCityFromDatabase): BaseUseCase<Int, Unit>{
    override suspend fun invoke(params: Int){
        return deleteCityFromDatabase.deleteCityFromDatabase(params)
    }
}