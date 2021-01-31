package com.ricardoteixeira.domain.models.future

data class GeneralFutureWeatherEntity(
    var dt: Int?,
    var dtTxt: String?,
    var mainFutureWeather: MainFutureWeatherEntity?,
    var descriptionFutureWeather: List<DescriptionFutureWeatherEntity?>?

)