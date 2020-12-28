package com.ricardoteixeira.data.remote.repository

import com.ricardoteixeira.app.framework.api.mappers.toDatabase
import com.ricardoteixeira.app.framework.api.models.WeatherCityApiModel
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

    fun decideWhereToFetch(): Flow<Result<List<WeatherCityDatabaseModel>>> = flow {

            getAllCities.getCurrentListOfCities().collect{
                if (it.isEmpty()) {
                    emit(Result.Success(data = emptyList<WeatherCityDatabaseModel>()))
                } else {
                    for (city in it){
                        val timestamp = System.currentTimeMillis().toString()
                        val newTimestamp = timestamp.dropLast(3).toInt()
                        if ( newTimestamp - city.requestTime > 40000 ) {
                            println("System ${System.currentTimeMillis() }")
                            println("city ${city.requestTime }")
                            val newWeather = fetchCityFromApi.fetchWeatherFromApi(city.cityName!!)
                            if (newWeather is Result.Success){
                                insertCityIntoDatabase.insertCityIntoDatabase(newWeather.data.toDatabase())
                                emit(Result.Success(data = it))
                            }
                        } else {
                            emit(Result.Success(data = it))
                        }
                    }

            }
        }
    }
}

interface FetchCityFromApi {

    suspend fun fetchWeatherFromApi(cityName: String): Result<WeatherCityApiModel>

}

interface InsertCityIntoDatabase{

    suspend fun insertCityIntoDatabase(city: WeatherCityDatabaseModel)

}

interface GetAllCities {

    suspend fun getCurrentListOfCities():Flow<List<WeatherCityDatabaseModel>>

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

