package com.ricardoteixeira.app.framework.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ricardoteixeira.app.framework.db.model.city.CityDatabaseModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: CityDatabaseModel): Long

    @Query("SELECT * FROM city WHERE city_name LIKE '%' || :searchQuery || '%' LIMIT 40")
    fun getCitiesByName(searchQuery: String): Flow<List<CityDatabaseModel>>
}