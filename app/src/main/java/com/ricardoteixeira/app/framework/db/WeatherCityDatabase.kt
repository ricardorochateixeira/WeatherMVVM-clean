package com.ricardoteixeira.app.framework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ricardoteixeira.app.framework.db.model.city.CityDatabaseModel
import com.ricardoteixeira.app.framework.db.model.future.FutureWeatherDatabaseModel
import com.ricardoteixeira.app.framework.db.model.current.CurrentWeatherDatabaseModel
import com.ricardoteixeira.app.framework.db.model.future.WeatherConverter

@Database(entities = [CurrentWeatherDatabaseModel::class,
                     FutureWeatherDatabaseModel::class,
                     CityDatabaseModel::class], version = 1)
@TypeConverters(WeatherConverter::class)

abstract class WeatherCityDatabase: RoomDatabase() {

    abstract fun weatherCityDao(): WeatherCityDao

    abstract fun futureWeatherDao(): FutureWeatherDao

    abstract fun cityDao(): CityDao

}