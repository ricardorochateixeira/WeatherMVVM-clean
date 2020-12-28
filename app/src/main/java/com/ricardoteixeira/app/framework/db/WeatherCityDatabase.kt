package com.ricardoteixeira.app.framework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ricardoteixeira.app.framework.db.model.WeatherCityDatabaseModel

@Database(entities = [WeatherCityDatabaseModel::class], version = 1)
abstract class WeatherCityDatabase: RoomDatabase() {

    abstract fun weatherCityDao(): WeatherCityDao

}