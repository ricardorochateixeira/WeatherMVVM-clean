package com.ricardoteixeira.app.framework.db.model.city

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "city")
data class CityDatabaseModel (
    @PrimaryKey(autoGenerate = false)
    val cityId: Int?,

    @ColumnInfo(name = "city_name")
    val cityName: String?,

    @ColumnInfo(name = "country_code")
    val countryCode: String?
)