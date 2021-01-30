package com.ricardoteixeira.app.presentation.splash

import android.app.Application
import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neovisionaries.i18n.CountryCode
import com.ricardoteixeira.app.framework.db.model.city.CityDatabaseModel
import com.ricardoteixeira.app.utils.PreferencesManager
import com.ricardoteixeira.app.utils.readJson
import com.ricardoteixeira.domain.usecases.details.GetCityByIdUseCase
import com.ricardoteixeira.domain.usecases.splash.InsertCityInformationIntoDatabaseUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*

class SplashViewModel
@ViewModelInject constructor(private val insertCityInformation: InsertCityInformationIntoDatabaseUseCase,
                             @ApplicationContext private val application: Context): ViewModel() {

     fun insertCityInformation() {
         val cities = readJson(application)
         viewModelScope.launch(Dispatchers.IO) {
             for (city in cities) {
                 insertCityInformation(city)
             }
         }
    }
}