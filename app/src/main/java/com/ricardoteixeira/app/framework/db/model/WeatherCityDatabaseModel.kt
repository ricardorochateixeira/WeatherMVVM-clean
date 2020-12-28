package com.ricardoteixeira.app.framework.db.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "city_weather")

data class WeatherCityDatabaseModel(
    @PrimaryKey(autoGenerate = false)
    val cityId: Int?,

    @ColumnInfo(name = "city_name")
    val cityName: String?,

    @ColumnInfo(name = "actual_temp")
    val actualTemp: Double?,

    @ColumnInfo(name = "feels_like_temp")
    val feelsLikeTemp: Double?,

    @ColumnInfo(name = "temp_min")
    val tempMin: Double?,

    @ColumnInfo(name = "temp_max")
    val tempMax: Double?,

    @ColumnInfo(name = "weather_Description")
    val weatherDescription: String?,

    @ColumnInfo(name = "weather_Id")
    val weatherId: Int?,

    @ColumnInfo(name = "request_time")
    val requestTime: Int,

    @ColumnInfo(name = "is_delete_pending")
    var isDeletePending: Boolean = false,

    @ColumnInfo(name = "request_time_system")
    val requestTimeSystem: Long = System.currentTimeMillis()

): Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WeatherCityDatabaseModel

        if (cityId != other.cityId) return false
        if (cityName != other.cityName) return false
        if (actualTemp != other.actualTemp) return false
        if (feelsLikeTemp != other.feelsLikeTemp) return false
        if (tempMin != other.tempMin) return false
        if (tempMax != other.tempMax) return false
        if (weatherDescription != other.weatherDescription) return false
        if (weatherId != other.weatherId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = cityId ?: 0
        result = 31 * result + (cityName?.hashCode() ?: 0)
        result = 31 * result + (actualTemp?.hashCode() ?: 0)
        result = 31 * result + (feelsLikeTemp?.hashCode() ?: 0)
        result = 31 * result + (tempMin?.hashCode() ?: 0)
        result = 31 * result + (tempMax?.hashCode() ?: 0)
        result = 31 * result + (weatherDescription?.hashCode() ?: 0)
        result = 31 * result + (weatherId ?: 0)
        return result
    }
}
