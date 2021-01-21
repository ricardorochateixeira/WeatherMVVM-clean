package com.ricardoteixeira.app.presentation.favorites

import com.ricardoteixeira.domain.models.current.CurrentWeatherEntityModel

data class FavoriteViewState (
    val error: String?,
    val result: List<CurrentWeatherEntityModel>
        )

