package com.ricardoteixeira.app.framework.db.model.future

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "future_weather")

data class FutureWeatherDatabaseModel(
    @PrimaryKey(autoGenerate = false)
    val cityId: Int?,

    @ColumnInfo(name = "city_name")
    val cityName: String?,

    @TypeConverters(WeatherConverter::class)
    @ColumnInfo(name = "list_future_weather")
    val generalFutureWeather: List<GeneralFutureWeatherDatabase>
)
