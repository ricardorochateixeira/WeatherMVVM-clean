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
import com.ricardoteixeira.domain.models.WeatherCityEntity
import com.ricardoteixeira.domain.usecases.DeleteCityUseCase
import com.ricardoteixeira.domain.usecases.GetAllCitiesUseCase
import com.ricardoteixeira.domain.usecases.FetchCityUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
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

    private val cities: ArrayList<WeatherCityEntity> = arrayListOf()


    suspend fun fetchCity(cityName: String) {
        when (val data = fetchCityUseCase(cityName)) {

            is Result.Success -> {
                cities.add(data.data)
                println("testeeeee $cities")
                _mutableMainState.value =
                    _mutableMainState.value?.copy(error = null, result = cities)
            }

            is Result.Failure -> _mutableMainState.value =
                _mutableMainState.value?.copy(isLoading = false, error = data.error)
        }
    }

    suspend fun getCities() {
        GlobalScope.launch(Dispatchers.IO) {
            val result = weatherRepository.decideWhereToFetch()
            when (result) {
                is Result.Success -> {
                    cities.addAll(result.data)
                    _mutableMainState.postValue(
                        ListCitiesViewState(
                            isLoading = false,
                            error = null,
                            result = cities
                        )
                    )
                    println("testeeeee $cities")
                }

                is Result.Failure -> _mutableMainState.postValue(
                    ListCitiesViewState(
                        isLoading = false,
                        error = result.error,
                        result = null
                    )
                )
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