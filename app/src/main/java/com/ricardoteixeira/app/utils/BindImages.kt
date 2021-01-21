package com.ricardoteixeira.app.utils

import android.widget.ImageView
import com.ricardoteixeira.domain.models.current.CurrentWeatherEntityModel
import com.ricardoteixeira.weathermvvm_clean.R

fun ImageView.setWeatherImage(item: CurrentWeatherEntityModel?) {
    item?.let {
        setImageResource(when (item.weatherId ) {
            in 200..299 -> R.drawable.ic_thunder
            in 300..399 -> R.drawable.ic_rain
            in 500..599 -> R.drawable.ic_rain
            in 600..699 -> R.drawable.ic_snow
            800 -> R.drawable.ic_sunny
            in 800..899 -> R.drawable.ic_cloudy_sunny
            else -> R.drawable.ic_cloudy_sunny
        })
    }
}