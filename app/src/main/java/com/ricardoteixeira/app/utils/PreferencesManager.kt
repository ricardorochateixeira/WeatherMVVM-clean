package com.ricardoteixeira.app.utils

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import androidx.datastore.preferences.preferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "PreferencesManager"

@Singleton
class PreferencesManager @Inject constructor(@ApplicationContext context: Context){

    private val dataStore = context.createDataStore("user_preferences")

    val preferencesFlow = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences", exception )
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val sortOrder = SortOrder.valueOf(
                preferences[PreferencesKeys.SORT_ORDER] ?: SortOrder.BY_DATE.toString()
            )
            FilterPreferences(sortOrder)
        }

    val cityIdFlow = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences", exception )
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val cityId = preferences[PreferencesKeys.CITY_ID] ?: 0
            CityIdToShowDetails(cityId!!)
        }

    private object PreferencesKeys {
        val SORT_ORDER = preferencesKey<String>("sort_order")
        val CITY_ID = preferencesKey<Int>("city_id")
    }

    suspend fun updateSortOrder(sortOrder: SortOrder) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SORT_ORDER] = sortOrder.name
        }
    }

    suspend fun updateCityId(cityIdToShow: Int) {
        dataStore.edit { cityId ->
            cityId[PreferencesKeys.CITY_ID] = cityIdToShow
        }
    }

}

enum class SortOrder {
    BY_NAME,
    BY_DATE,
    BY_FAVORITE
}

data class FilterPreferences(val sortOrder: SortOrder)

data class CityIdToShowDetails(val cityId: Int)
