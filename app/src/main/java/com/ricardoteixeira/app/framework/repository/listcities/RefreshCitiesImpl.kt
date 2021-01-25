package com.ricardoteixeira.app.framework.repository.listcities

import com.ricardoteixeira.app.framework.db.WeatherCityDao
import com.ricardoteixeira.app.framework.db.mappers.toDatabase
import com.ricardoteixeira.app.framework.db.mappers.toEntity
import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.repository.FetchCityFromApi
import com.ricardoteixeira.data.repository.RefreshCities
import com.ricardoteixeira.domain.models.current.CurrentWeatherEntityModel
import javax.inject.Inject

class RefreshCitiesImpl
    @Inject constructor(private val weatherCityDao: WeatherCityDao, private val fetchCityFromApi: FetchCityFromApi): RefreshCities {
    override suspend fun refreshCities(currentListOfCities: MutableList<CurrentWeatherEntityModel>): Result<List<CurrentWeatherEntityModel?>> {
        for (city in currentListOfCities) {
            val newWeather = city.cityName?.let { fetchCityFromApi.fetchWeatherFromApi(it) }
            if (newWeather is Result.Success) {
                weatherCityDao.insertCity(newWeather.data.toDatabase())
                val index = currentListOfCities.indexOf(city)
                val newWeatherToInsert = newWeather.data.toDatabase().toEntity()
                currentListOfCities[index].copy(actualTemp = newWeatherToInsert.actualTemp,
                tempMin = newWeatherToInsert.tempMin,
                tempMax = newWeatherToInsert.tempMax,
                feelsLikeTemp = newWeatherToInsert.feelsLikeTemp,
                weatherId = newWeatherToInsert.weatherId,
                weatherDescription = newWeatherToInsert.weatherDescription,
                requestTime = newWeatherToInsert.requestTime)
                weatherCityDao.insertCity(currentListOfCities[index].toDatabase())
            } else {
                Result.Failure(error = "Error fetching city")
            }
        }
        return Result.Success(data = currentListOfCities.toList())
    }
}