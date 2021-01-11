package com.ricardoteixeira.app.presentation.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ricardoteixeira.app.presentation.listcities.*
import com.ricardoteixeira.app.utils.PreferencesManager
import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.app.utils.SortOrder
import com.ricardoteixeira.domain.models.WeatherCityEntity
import com.ricardoteixeira.domain.usecases.details.GetCityByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailsViewModel
@ViewModelInject constructor(private val getCityByIdUseCase: GetCityByIdUseCase,
                             private val preferences: PreferencesManager): ViewModel() {


    private val _mutableDetailState: MutableLiveData<WeatherCityEntity?> = MutableLiveData()
    val detailState: LiveData<WeatherCityEntity?>
        get() = _mutableDetailState

    private val cityIdFlow = preferences.cityIdFlow

    fun getCityById(cityId: Int) {

        if (cityId == 0) {
            viewModelScope.launch(Dispatchers.IO) {
                cityIdFlow.collect {
                    val cityIdPreferences = it.cityId
                    when (val city = getCityByIdUseCase(cityIdPreferences)) {
                        is Result.Success -> _mutableDetailState.postValue(city.data)
                        is Result.Failure -> _mutableDetailState.postValue(null)
                    }
                }
            }
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                when (val city = getCityByIdUseCase(cityId)) {
                    is Result.Success -> _mutableDetailState.postValue(city.data)
                    is Result.Failure -> _mutableDetailState.postValue(null)
                }
            }
        }
    }
}