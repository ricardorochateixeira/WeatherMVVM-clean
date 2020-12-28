package com.ricardoteixeira.app.framework.api.repositoryImpl

import android.widget.Toast
import com.ricardoteixeira.app.framework.api.ApiHelper
import com.ricardoteixeira.app.framework.api.WeatherService
import com.ricardoteixeira.app.framework.api.mappers.toDatabase
import com.ricardoteixeira.app.framework.api.models.WeatherCityApiModel
import com.ricardoteixeira.app.framework.db.WeatherCityDao
import com.ricardoteixeira.app.framework.db.mappers.toEntity
import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.remote.mappers.toEntity
import com.ricardoteixeira.data.remote.repository.InsertNewCity
import com.ricardoteixeira.domain.models.WeatherCityEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class InsertNewCityImpl
@Inject constructor(private val weatherDao: WeatherCityDao,
                    private val apiHelper: ApiHelper
): InsertNewCity {

    override suspend fun insertNewCity(cityName: String)  {
        val result = apiHelper.getWeather(cityName)

        when (result) {
            is Result.Success -> weatherDao.insertCity(city = result.data.toDatabase())

            is Result.Failure -> result.error
        }
    }

}