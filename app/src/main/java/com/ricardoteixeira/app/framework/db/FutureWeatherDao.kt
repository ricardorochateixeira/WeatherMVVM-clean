package com.ricardoteixeira.app.framework.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.ricardoteixeira.app.framework.db.model.future.FutureWeatherDatabaseModel

@Dao
interface FutureWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: FutureWeatherDatabaseModel): Long
}
