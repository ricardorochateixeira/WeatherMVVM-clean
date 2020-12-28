package com.ricardoteixeira.app.ui.listcities

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Update
import com.ricardoteixeira.app.framework.api.mappers.toDatabase
import com.ricardoteixeira.app.framework.api.models.WeatherCityApiModel
import com.ricardoteixeira.app.framework.db.mappers.toDatabase
import com.ricardoteixeira.app.framework.db.mappers.toEntity
import com.ricardoteixeira.app.framework.db.model.WeatherCityDatabaseModel
import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.data.remote.mappers.toEntity
import com.ricardoteixeira.data.remote.repository.InsertCityIntoDatabase
import com.ricardoteixeira.data.remote.repository.UpdateCity
import com.ricardoteixeira.data.remote.repository.WeatherRepository
import com.ricardoteixeira.domain.usecases.DeleteCityUseCase
import com.ricardoteixeira.domain.usecases.GetAllCitiesUseCase
import com.ricardoteixeira.domain.usecases.FetchCityUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ListCitiesViewModel
@ViewModelInject constructor(var fetchCityUseCase: FetchCityUseCase,
                             var insertCityIntoDatabase: InsertCityIntoDatabase,
                             var weatherRepository: WeatherRepository,
                             var updateCity:UpdateCity,
                             var deleteCityUseCase: DeleteCityUseCase): ViewModel() {

    private val _mutableMainState: MutableLiveData<ListCitiesViewState> = MutableLiveData()
    val mainState: LiveData<ListCitiesViewState>
        get() = _mutableMainState

    suspend fun fetchCity(cityName: String) {
        when (val data = fetchCityUseCase(cityName)) {
            is Result.Success -> {
                _mutableMainState.value =
                    _mutableMainState.value?.copy(error = null, result = mutableListOf(data.data.toDatabase()))
                insertCityIntoDatabase(data.data)
            }

            is Result.Failure -> _mutableMainState.value =
                _mutableMainState.value?.copy(isLoading = false, error = data.error)
        }
    }

    suspend fun insertCityIntoDatabase(city: WeatherCityApiModel) {
        insertCityIntoDatabase.insertCityIntoDatabase(city.toDatabase())
    }

    suspend fun getCities() {
        weatherRepository.decideWhereToFetch().collect { result ->
            when(result) {
                is Result.Success -> _mutableMainState.value = ListCitiesViewState(isLoading = false, error = null, result = result.data)

                is Result.Failure -> _mutableMainState.value = ListCitiesViewState(isLoading = false, error = result.error, result = null)
            }
        }
    }

    fun isDeletePending(cityDatabaseModel: WeatherCityDatabaseModel?) {
        val cityUpdated = cityDatabaseModel?.copy(isDeletePending = true)
        viewModelScope.launch {
            updateCity.updateCity(cityUpdated!!)
        }
    }


}