package com.ricardoteixeira.app.framework.db.model.city

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class CityModel(
    val id: Int?,

    val name: String?,

    val country: String?
)

