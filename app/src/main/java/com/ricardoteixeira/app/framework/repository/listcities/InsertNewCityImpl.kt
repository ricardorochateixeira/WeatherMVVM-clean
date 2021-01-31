package com.ricardoteixeira.app.framework.repository.listcities

import com.ricardoteixeira.app.framework.api.ApiHelper
import com.ricardoteixeira.app.framework.api.mappers.currentweather.toDatabase
import com.ricardoteixeira.app.framework.db.WeatherCityDao
import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.repository.InsertNewCity
import javax.inject.Inject

class InsertNewCityImpl
@Inject constructor(
    private val weatherDao: WeatherCityDao,
    private val apiHelper: ApiHelper
) : InsertNewCity {

    override suspend fun insertNewCity(cityName: String) {
        val result = apiHelper.getWeatherByName(cityName)

        when (result) {
            is Result.Success -> weatherDao.insertCity(city = result.data.toDatabase())

            is Result.Failure -> result.error
        }
    }
}