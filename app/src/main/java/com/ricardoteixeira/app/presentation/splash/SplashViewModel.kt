package com.ricardoteixeira.app.presentation.splash

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ricardoteixeira.app.utils.readJson
import com.ricardoteixeira.domain.usecases.splash.InsertCityInformationIntoDatabaseUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashViewModel
@ViewModelInject constructor(
    private val insertCityInformation: InsertCityInformationIntoDatabaseUseCase,
    @ApplicationContext private val application: Context
) : ViewModel() {

    fun insertCityInformation() {
        val cities = readJson(application)
        viewModelScope.launch(Dispatchers.IO) {
            for (city in cities) {
                insertCityInformation(city)
            }
        }
    }
}