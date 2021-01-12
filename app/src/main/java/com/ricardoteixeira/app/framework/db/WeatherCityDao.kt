package com.ricardoteixeira.app.framework.db

import androidx.room.*
import com.ricardoteixeira.app.framework.db.model.WeatherCityDatabaseModel
import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.app.utils.SortOrder
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

@Dao
interface WeatherCityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: WeatherCityDatabaseModel): Long

    @Query("DELETE FROM city_weather WHERE cityId = :cityId")
    suspend fun deleteCity(cityId: Int): Int

    @Query("SELECT * FROM  city_weather WHERE is_delete_pending = 0 ORDER BY request_time DESC")
    fun getCities(): List<WeatherCityDatabaseModel>

    @Update
    suspend fun updateCity(city: WeatherCityDatabaseModel)

    @Query("SELECT * FROM  city_weather WHERE is_delete_pending = 1")
    fun getCityPendingDelete(): WeatherCityDatabaseModel?

    @Query("SELECT * FROM  city_weather WHERE cityId = :cityId")
    fun getCityById(cityId: Int): WeatherCityDatabaseModel?

    @Query("SELECT * FROM  city_weather WHERE is_favorite = 1")
    fun getFavoriteCities(): List<WeatherCityDatabaseModel?>



}