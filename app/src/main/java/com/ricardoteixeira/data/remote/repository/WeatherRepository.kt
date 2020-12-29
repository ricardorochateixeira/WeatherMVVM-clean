package com.ricardoteixeira.data.remote.repository

import com.ricardoteixeira.app.framework.api.mappers.toDatabase
import com.ricardoteixeira.app.framework.api.models.WeatherCityApiModel
import com.ricardoteixeira.app.framework.db.mappers.toDatabase
import com.ricardoteixeira.app.framework.db.mappers.toEntity
import com.ricardoteixeira.app.framework.db.model.WeatherCityDatabaseModel
import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.domain.models.WeatherCityEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class WeatherRepository(
    private val getAllCities: GetAllCities,

    private val fetchCityFromApi: FetchCityFromApi,

    private val insertCityIntoDatabase:InsertCityIntoDatabase) {

    suspend fun decideWhereToFetch(): Result<List<WeatherCityEntity>> {

            val cities = getAllCities.getCurrentListOfCities()
                if (cities.isEmpty()) {
                    return Result.Success(data = emptyList<WeatherCityEntity>())
                } else {
                    for (city in cities){
                        val timestamp = System.currentTimeMillis().toString()
                        val newTimestamp = timestamp.dropLast(3).toInt()
                        if ( newTimestamp - city.requestTime > 40000 ) {
                            val newWeather = fetchCityFromApi.fetchWeatherFromApi(city.cityName!!)
                            return if (newWeather is Result.Success){
                                insertCityIntoDatabase.insertCityIntoDatabase(newWeather.data.toDatabase())
                                Result.Success(data = cities.map { it.toEntity() })
                            } else {
                                Result.Failure(error = error("error"))
                            }
                        } else {
                            return Result.Success(data = cities.map { it.toEntity() })
                        }
                    }
                }
        return Result.Failure("Error")
        }
    }

interface FetchCityFromApi {

    suspend fun fetchWeatherFromApi(cityName: String): Result<WeatherCityEntity>

}

interface InsertCityIntoDatabase{

    suspend fun insertCityIntoDatabase(city: WeatherCityDatabaseModel)

}

interface GetAllCities {

    suspend fun getCurrentListOfCities():List<WeatherCityDatabaseModel>

}

interface InsertNewCity {

    suspend fun insertNewCity(cityName: String)

}

interface DeleteCityFromDatabase {

    suspend fun deleteCityFromDatabase(cityId: Int)
}

interface UpdateCity {
    suspend fun updateCity(city: WeatherCityDatabaseModel)
}

