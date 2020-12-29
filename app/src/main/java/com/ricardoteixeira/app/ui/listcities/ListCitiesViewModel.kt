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

const val FETCH_CITY_SUCCESS_MESSAGE = "City Added Successfully"
const val FETCH_CITY_ERROR_MESSAGE = "Problem adding city! Try again."

const val CITIES_LOADED_SUCCESS_MESSAGE = "Cities loaded successfully"
const val CITIES_LOADED_ERROR_MESSAGE = "Problem while loading cities"

class ListCitiesViewModel
@ViewModelInject constructor(var fetchCityUseCase: FetchCityUseCase,
                             var insertCityIntoDatabase: InsertCityIntoDatabase,
                             var weatherRepository: WeatherRepository,
                             var updateCity:UpdateCity,
                             var deleteCityUseCase: DeleteCityUseCase): ViewModel() {

    private val _mutableMainState: MutableLiveData<ListCitiesViewState> = MutableLiveData()
    val mainState: LiveData<ListCitiesViewState>
        get() = _mutableMainState

    val cities: ArrayList<WeatherCityEntity> = arrayListOf()


    suspend fun fetchCity(cityName: String){
        when (val data = fetchCityUseCase(cityName)) {

            is Result.Success -> {
                if (cities.contains(data.data)) {
                    cities.remove(data.data)
                }
                cities.add(data.data)
                _mutableMainState.value =
                    _mutableMainState.value?.copy(error = null, result = cities, responseType = ResponseType(message = FETCH_CITY_SUCCESS_MESSAGE, uiComponentType = UIComponentType.SnackBar(), messageType = MessageType.Success()))
            }

            is Result.Failure -> _mutableMainState.value =
                _mutableMainState.value?.copy(isLoading = false, error = data.error, responseType = ResponseType(message = FETCH_CITY_ERROR_MESSAGE, uiComponentType = UIComponentType.SnackBar(), messageType = MessageType.Error()))
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
                            result = cities,
                            responseType = ResponseType(
                                message = CITIES_LOADED_SUCCESS_MESSAGE,
                                uiComponentType = UIComponentType.SnackBar(),
                                messageType = MessageType.Success()
                            )
                        )
                    )
                }

                is Result.Failure -> _mutableMainState.postValue(
                    ListCitiesViewState(
                        isLoading = false,
                        error = result.error,
                        result = null,
                        responseType = ResponseType(message = CITIES_LOADED_ERROR_MESSAGE, uiComponentType = UIComponentType.SnackBar(), messageType = MessageType.Error())
                    )
                )
        }
        }
    }

    fun isDeletePending(cities: ArrayList<WeatherCityEntity>, cityWeatherEntity: WeatherCityEntity?) {
        val cityUpdated = cityWeatherEntity?.copy(isUpdatePending = true)
        cities.remove(cityWeatherEntity)
        viewModelScope.launch {
            updateCity.updateCity(cityUpdated!!.toDatabase())
        }
    }

}