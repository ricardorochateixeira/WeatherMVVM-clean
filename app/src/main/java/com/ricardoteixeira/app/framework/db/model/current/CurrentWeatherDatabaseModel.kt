package com.ricardoteixeira.app.framework.db.model.current

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat

@Parcelize
@Entity(tableName = "city_weather")

data class CurrentWeatherDatabaseModel(
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

    @ColumnInfo(name = "humidity")
    var humidity: Int?,

    @ColumnInfo(name = "wind_speed")
    var windSpeed: Double?,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false,

    @ColumnInfo(name = "request_time_system")
    val requestTimeSystem: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "sunset")
    val sunset: Int?,

    @ColumnInfo(name = "sunrise")
    val sunrise: Int?


) : Parcelable {

    val sunsetFormated: String
        get() = DateFormat.getDateTimeInstance().format(sunset?.toLong())

    val sunriseFormated: String
        get() = DateFormat.getDateTimeInstance().format(sunrise?.toLong())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CurrentWeatherDatabaseModel

        if (cityId != other.cityId) return false
        if (cityName != other.cityName) return false


        return true
    }

    override fun hashCode(): Int {
        var result = cityId ?: 0
        result = 31 * result + (cityName?.hashCode() ?: 0)

        return result
    }
}
