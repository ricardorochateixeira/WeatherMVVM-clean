package com.ricardoteixeira.app.framework.db.model.future

data class MainFutureWeatherDatabase(
    var feelsLike: Double?,
    var temp: Double?,
    var tempKf: Double?,
    var tempMax: Double?,
    var tempMin: Double?
)