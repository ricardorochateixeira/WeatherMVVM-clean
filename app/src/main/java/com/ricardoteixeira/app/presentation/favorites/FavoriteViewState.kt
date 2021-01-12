package com.ricardoteixeira.app.presentation.favorites

import com.ricardoteixeira.app.presentation.listcities.ResponseType
import com.ricardoteixeira.domain.models.WeatherCityEntity

data class FavoriteViewState (
    val error: String?,
    val result: List<WeatherCityEntity>
        )

