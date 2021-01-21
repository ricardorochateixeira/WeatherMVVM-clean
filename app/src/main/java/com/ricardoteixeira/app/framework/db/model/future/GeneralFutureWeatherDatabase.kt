package com.ricardoteixeira.app.framework.db.model.future

data class GeneralFutureWeatherDatabase(
    var dt: Int?,
    var dtTxt: String?,
    var mainFutureWeather: MainFutureWeatherDatabase?,
    var descriptionFutureWeather: List<DescriptionFutureWeatherDatabase?>?
)
