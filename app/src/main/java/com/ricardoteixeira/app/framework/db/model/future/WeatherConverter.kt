package com.ricardoteixeira.app.framework.db.model.future

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WeatherConverter {

    @TypeConverter
    fun fromString(value: String?): List<GeneralFutureWeatherDatabase?>? {
        val listType = object : TypeToken<List<GeneralFutureWeatherDatabase?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromAList(list: List<GeneralFutureWeatherDatabase?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }


}