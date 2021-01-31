package com.ricardoteixeira.app.presentation.future

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ricardoteixeira.app.utils.PreferencesManager
import com.ricardoteixeira.domain.usecases.futureweather.GetFutureWeatherFromDatabaseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FutureDaysViewModel
@ViewModelInject constructor(
    private val getFutureWeatherFromDatabaseUseCase: GetFutureWeatherFromDatabaseUseCase,
    val preferences: PreferencesManager
) : ViewModel() {

    private val _mutableFutureState: MutableLiveData<FutureWeatherViewState> = MutableLiveData()
    val futureState: LiveData<FutureWeatherViewState>
        get() = _mutableFutureState

    private val cityIdFlow = preferences.cityIdFlow

    fun getFutureWeatherCity(cityId: Int) {
        if (cityId == 0) {
            viewModelScope.launch(Dispatchers.IO) {
                cityIdFlow.collect {
                    val cityIdPreferences = it.cityId
                    val city = getFutureWeatherFromDatabaseUseCase.getFutureWeatherFromDatabase(
                        cityIdPreferences
                    )
                    _mutableFutureState.postValue(
                        FutureWeatherViewState(
                            error = null,
                            result = city
                        )
                    )
                }
            }
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                val city = getFutureWeatherFromDatabaseUseCase.getFutureWeatherFromDatabase(cityId)
                _mutableFutureState.postValue(FutureWeatherViewState(error = null, result = city))
            }

        }

    }
}