package com.ricardoteixeira.app.framework.db

import androidx.room.*
import com.ricardoteixeira.app.framework.db.model.WeatherCityDatabaseModel
import com.ricardoteixeira.app.utils.Result
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherCityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: WeatherCityDatabaseModel): Long

    @Query("DELETE FROM city_weather WHERE cityId = :cityId")
    suspend fun deleteCity(cityId: Int): Int

    @Query("SELECT * FROM  city_weather WHERE is_delete_pending = 0  ORDER BY request_time_system DESC")
    fun getAllCities(): Flow<List<WeatherCityDatabaseModel>>

    @Update
    suspend fun updateCity(city: WeatherCityDatabaseModel)


}