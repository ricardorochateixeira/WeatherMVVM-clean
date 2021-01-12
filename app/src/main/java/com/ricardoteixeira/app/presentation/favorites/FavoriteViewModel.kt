package com.ricardoteixeira.app.presentation.favorites

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ricardoteixeira.app.presentation.listcities.ListCitiesViewState
import com.ricardoteixeira.domain.models.WeatherCityEntity
import com.ricardoteixeira.domain.usecases.favorite.GetFavoriteCitiesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteViewModel @ViewModelInject constructor(
    private val getFavoriteCitiesUseCase: GetFavoriteCitiesUseCase
): ViewModel() {

    private val _mutableMainState: MutableLiveData<FavoriteViewState> = MutableLiveData()
    val mainState: LiveData<FavoriteViewState>
        get() = _mutableMainState

    fun getFavoriteCities() {
        viewModelScope.launch(Dispatchers.IO) {
            val cities = getFavoriteCitiesUseCase(Unit)
            _mutableMainState.postValue(
                FavoriteViewState(
                    error = null,
                    result = cities
                )
            )
        }
    }
}