package com.ricardoteixeira.app.presentation.favorites

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ricardoteixeira.domain.usecases.favorite.GetFavoriteCitiesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel @ViewModelInject constructor(
    private val getFavoriteCitiesUseCase: GetFavoriteCitiesUseCase
): ViewModel() {

    private val _mutableFavoriteState: MutableLiveData<FavoriteViewState> = MutableLiveData()
    val favoriteState: LiveData<FavoriteViewState>
        get() = _mutableFavoriteState

    fun getFavoriteCities() {
        viewModelScope.launch(Dispatchers.IO) {
            val cities = getFavoriteCitiesUseCase(Unit)
            _mutableFavoriteState.postValue(
                FavoriteViewState(
                    error = null,
                    result = cities
                )
            )
        }
    }
}