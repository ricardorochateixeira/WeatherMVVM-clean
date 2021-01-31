package com.ricardoteixeira.app.presentation.future

import com.ricardoteixeira.domain.models.future.FutureWeatherEntityModel

data class FutureWeatherViewState(
    val error: String?,
    val result: FutureWeatherEntityModel
)

