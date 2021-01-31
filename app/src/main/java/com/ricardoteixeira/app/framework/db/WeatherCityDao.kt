package com.ricardoteixeira.app.framework.db

import androidx.room.*
import com.ricardoteixeira.app.framework.db.model.current.CurrentWeatherDatabaseModel

@Dao
interface WeatherCityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: CurrentWeatherDatabaseModel): Long

    @Query("DELETE FROM city_weather WHERE cityId = :cityId")
    suspend fun deleteCity(cityId: Int): Int

    @Query("SELECT * FROM  city_weather  ORDER BY request_time DESC")
    fun getCities(): List<CurrentWeatherDatabaseModel>

    @Update
    suspend fun updateCity(city: CurrentWeatherDatabaseModel)

    @Query("SELECT * FROM  city_weather WHERE is_delete_pending = 1")
    fun getCityPendingDelete(): CurrentWeatherDatabaseModel?

    @Query("SELECT * FROM  city_weather WHERE cityId = :cityId")
    fun getCityById(cityId: Int): CurrentWeatherDatabaseModel?

    @Query("SELECT * FROM  city_weather WHERE is_favorite = 1")
    fun getFavoriteCities(): List<CurrentWeatherDatabaseModel?>


}