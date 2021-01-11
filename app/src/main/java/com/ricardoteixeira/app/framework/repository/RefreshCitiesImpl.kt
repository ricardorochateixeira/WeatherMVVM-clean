package com.ricardoteixeira.app.framework.repository

import com.ricardoteixeira.app.framework.db.WeatherCityDao
import com.ricardoteixeira.app.framework.db.mappers.toDatabase
import com.ricardoteixeira.app.framework.db.mappers.toEntity
import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.repository.FetchCityFromApi
import com.ricardoteixeira.data.repository.RefreshCities
import com.ricardoteixeira.domain.models.WeatherCityEntity
import javax.inject.Inject

class RefreshCitiesImpl
    @Inject constructor(private val weatherCityDao: WeatherCityDao, private val fetchCityFromApi: FetchCityFromApi): RefreshCities {
    override suspend fun refreshCities(cities: MutableList<WeatherCityEntity>): Result<List<WeatherCityEntity?>> {
        for (city in cities) {
            val newWeather = city.cityName?.let { fetchCityFromApi.fetchWeatherFromApi(it) }
            if (newWeather is Result.Success) {
                weatherCityDao.insertCity(newWeather.data.toDatabase())
                val index = cities.indexOf(city)
                val newWeather = newWeather.data.toDatabase().toEntity()
                cities[index].copy(actualTemp = newWeather.actualTemp,
                tempMin = newWeather.tempMin,
                tempMax = newWeather.tempMax,
                feelsLikeTemp = newWeather.feelsLikeTemp,
                weatherId = newWeather.weatherId,
                weatherDescription = newWeather.weatherDescription,
                requestTime = newWeather.requestTime)
                weatherCityDao.insertCity(cities[index].toDatabase())
            } else {
                Result.Failure(error = "Error fetching city")
            }
        }
        return Result.Success(data = cities.toList())
        return Result.Failure("Error")
    }
}