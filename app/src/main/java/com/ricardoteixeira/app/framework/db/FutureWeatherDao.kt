package com.ricardoteixeira.app.framework.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ricardoteixeira.app.framework.db.model.future.FutureWeatherDatabaseModel

@Dao
interface FutureWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: FutureWeatherDatabaseModel): Long

    @Query("SELECT * FROM  future_weather WHERE cityId = :cityId")
    fun getCityById(cityId: Int): FutureWeatherDatabaseModel?
}
