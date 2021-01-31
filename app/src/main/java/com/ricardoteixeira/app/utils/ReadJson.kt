package com.ricardoteixeira.app.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ricardoteixeira.app.framework.db.mappers.city.toDatabaseModel
import com.ricardoteixeira.app.framework.db.model.city.CityDatabaseModel
import com.ricardoteixeira.app.framework.db.model.city.CityModel
import java.io.IOException

fun readJson(context: Context): List<CityDatabaseModel> {
    val jsonFileString = getJsonDataFromAsset(context, "city.json")
    val gson = Gson()
    val listCities = object : TypeToken<List<CityModel>>() {}.type
    val cities: List<CityModel> = gson.fromJson(jsonFileString, listCities)
    return cities.map { it.toDatabaseModel() }

}

fun getJsonDataFromAsset(context: Context, fileName: String): String? {
    val jsonString: String
    try {
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    return jsonString
}