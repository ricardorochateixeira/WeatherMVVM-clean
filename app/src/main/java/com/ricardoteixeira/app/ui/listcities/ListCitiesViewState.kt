package com.ricardoteixeira.app.ui.listcities

import androidx.annotation.StringRes
import com.ricardoteixeira.app.framework.db.model.WeatherCityDatabaseModel
import com.ricardoteixeira.domain.models.WeatherCityEntity

data class ListCitiesViewState(
    val isLoading: Boolean,
    val error: String?,
    val result: List<WeatherCityEntity?>?
)
