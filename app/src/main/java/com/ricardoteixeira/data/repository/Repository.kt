package com.ricardoteixeira.data.repository

import com.ricardoteixeira.app.framework.db.mappers.toDatabase
import com.ricardoteixeira.app.framework.db.mappers.toEntity
import com.ricardoteixeira.app.framework.db.model.current.CurrentWeatherDatabaseModel
import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.domain.models.current.CurrentWeatherEntityModel
import com.ricardoteixeira.domain.models.future.FutureWeatherEntityModel

class WeatherRepository(
    private val getAllCities: GetAllCities,

    private val fetchCityFromApi: FetchCityFromApi,

    private val insertCityIntoDatabase: InsertCityIntoDatabase
) {

    suspend fun decideWhereToFetch(): Result<List<CurrentWeatherEntityModel>> {

        val cities = getAllCities.getAllCities().toMutableList()
        if (cities.isEmpty()) {
            return Result.Success(data = emptyList<CurrentWeatherEntityModel>())
        } else {
            for (city in cities) {
                val timestamp = System.currentTimeMillis().toString()
                val newTimestamp = timestamp.dropLast(3).toInt()
                if (newTimestamp - city.requestTime > 40000) {
                    val newWeather = fetchCityFromApi.fetchWeatherFromApi(city.cityName!!)
                    if (newWeather is Result.Success) {
                        val index = cities.indexOf(city)
                        val newCity = newWeather.data.toDatabase()
                        cities[index].copy(
                            actualTemp = newWeather.data.actualTemp,
                            tempMin = newWeather.data.tempMin,
                            tempMax = newWeather.data.tempMax,
                            feelsLikeTemp = newWeather.data.feelsLikeTemp,
                            weatherId = newWeather.data.weatherId,
                            weatherDescription = newWeather.data.weatherDescription,
                            requestTime = newWeather.data.requestTime)
                        insertCityIntoDatabase.insertCityIntoDatabase(newCity)
                    }
                    return Result.Success(data = cities.map { it.toEntity() })
                } else {
                    return Result.Success(data = cities.map { it.toEntity() }
                        .sortedByDescending { it.requestTime })
                }
            }
        }
        return Result.Failure("Error")
    }
}

interface FetchCityFromApi {

    suspend fun fetchWeatherFromApi(cityName: String): Result<CurrentWeatherEntityModel>

}

interface InsertCityIntoDatabase {

    suspend fun insertCityIntoDatabase(city: CurrentWeatherDatabaseModel)

}

interface GetAllCities {

    suspend fun getAllCities(): List<CurrentWeatherDatabaseModel>

}

interface InsertNewCity {

    suspend fun insertNewCity(cityName: String)

}

interface DeleteCityFromDatabase {

    suspend fun deleteCityFromDatabase(cityId: Int)
}

interface UpdateCity {
    suspend fun updateCity(city: CurrentWeatherDatabaseModel)
}

interface GetCityPendingDelete {
    suspend fun getCityPendingDelete(): CurrentWeatherEntityModel?
}

interface RefreshCities {
    suspend fun refreshCities(currentListOfCities: MutableList<CurrentWeatherEntityModel>): Result<List<CurrentWeatherEntityModel?>>
}

interface GetCityById {
    suspend fun getCityById(cityId: Int): Result<CurrentWeatherEntityModel>
}

interface GetFavoriteCities {
    suspend fun getFavoriteCities(): List<CurrentWeatherEntityModel>
}

interface FetchFutureWeatherFromApi {
    suspend fun fetchFutureWeatherFromApi(cityName: String): Result<FutureWeatherEntityModel>
}

interface GetFutureWeatherFromDatabase{
    suspend fun getFutureWeatherFromDatabase(cityId: Int): FutureWeatherEntityModel
}
