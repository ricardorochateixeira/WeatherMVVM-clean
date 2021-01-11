package com.ricardoteixeira.app.presentation.listcities

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ricardoteixeira.app.framework.db.mappers.toDatabase
import com.ricardoteixeira.app.utils.FilterPreferences
import com.ricardoteixeira.app.utils.PreferencesManager
import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.app.utils.SortOrder
import com.ricardoteixeira.data.repository.InsertCityIntoDatabase
import com.ricardoteixeira.data.repository.UpdateCity
import com.ricardoteixeira.data.repository.WeatherRepository
import com.ricardoteixeira.domain.models.WeatherCityEntity
import com.ricardoteixeira.domain.usecases.listcities.DeleteCityUseCase
import com.ricardoteixeira.domain.usecases.listcities.FetchCityUseCase
import com.ricardoteixeira.domain.usecases.listcities.GetCityPendingDeleteUseCase
import com.ricardoteixeira.domain.usecases.listcities.RefreshCitiesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

const val FETCH_CITY_SUCCESS_MESSAGE = "City Added Successfully"
const val FETCH_CITY_ERROR_MESSAGE = "Problem adding city! Try again."

const val CITIES_LOADED_SUCCESS_MESSAGE = "Cities loaded successfully"
const val CITIES_EMPTY_LOADED_SUCCESS_MESSAGE = "No cities in database"
const val CITIES_LOADED_ERROR_MESSAGE = "Problem while loading cities"

const val CITY_RESTORED_SUCCESSFULLY = "City restored successfully"
const val ITEM_REMOVED = "1 item removed from the list"

const val UPDATE_CITY_SUCCESS_MESSAGE = "Cities refreshed successfully"
const val UPDATE_CITY_ERROR_MESSAGE = "Cities updated failed!"

class ListCitiesViewModel
@ViewModelInject constructor(
    var fetchCityUseCase: FetchCityUseCase,
    var insertCityIntoDatabase: InsertCityIntoDatabase,
    var weatherRepository: WeatherRepository,
    var updateCity: UpdateCity,
    var getCityPendingDeleteUseCase: GetCityPendingDeleteUseCase,
    var deleteCityUseCase: DeleteCityUseCase,
    var refreshCitiesUseCase: RefreshCitiesUseCase,
    private val preferences: PreferencesManager
) : ViewModel() {

    private val _mutableMainState: MutableLiveData<ListCitiesViewState> = MutableLiveData()
    val mainState: LiveData<ListCitiesViewState>
        get() = _mutableMainState

    var cities: ArrayList<WeatherCityEntity> = arrayListOf()
    var citiesList: List<WeatherCityEntity> = listOf()

    val preferencesFlow = preferences.preferencesFlow.asLiveData()


    fun fetchCity(cityName: String) {
        viewModelScope.launch {
            citiesList = cities.toList()
            when (val data = fetchCityUseCase(cityName)) {
                is Result.Success -> {
                    cities.add(0, data.data)
                    citiesList = cities.toList()
                    _mutableMainState.value =
                        _mutableMainState.value?.copy(
                            isShowingSnackBar = true, error = null, result = citiesList,
                            responseType = ResponseType(
                                uiComponentType = UIComponentType.SnackBar(
                                    message = FETCH_CITY_SUCCESS_MESSAGE,
                                    undoCallback = false
                                ), messageType = MessageType.Success()
                            )
                        )
                }

                is Result.Failure -> _mutableMainState.value =
                    _mutableMainState.value?.copy(
                        isShowingSnackBar = true,
                        error = data.error,
                        responseType = ResponseType(
                            uiComponentType = UIComponentType.SnackBar(message = FETCH_CITY_ERROR_MESSAGE),
                            messageType = MessageType.Error()
                        )
                    )
            }
        }
    }

    fun getCities() {
        viewModelScope.launch(Dispatchers.IO) {

            when (val result = weatherRepository.decideWhereToFetch()) {
                is Result.Success -> {
                    if (result.data.isEmpty()) {
                        _mutableMainState.postValue(
                            ListCitiesViewState(
                                isShowingSnackBar = true,
                                error = null,
                                result = listOf<WeatherCityEntity>(),
                                responseType = ResponseType(
                                    uiComponentType = UIComponentType.SnackBar(message = CITIES_EMPTY_LOADED_SUCCESS_MESSAGE),
                                    messageType = MessageType.Success()
                                )
                            )
                        )
                    } else {
                        cities = arrayListOf()
                        cities.addAll(result.data.filter { !it.isUpdatePending })
                        citiesList = cities.toList()
                        _mutableMainState.postValue(
                            ListCitiesViewState(
                                isShowingSnackBar = true,
                                error = null,
                                result = citiesList,
                                responseType = ResponseType(
                                    uiComponentType = UIComponentType.SnackBar(message = CITIES_LOADED_SUCCESS_MESSAGE),
                                    messageType = MessageType.Success()
                                )
                            )
                        )
                    }
                }

                is Result.Failure -> _mutableMainState.postValue(
                    ListCitiesViewState(
                        isShowingSnackBar = true,
                        error = result.error,
                        result = listOf<WeatherCityEntity>(),
                        responseType = ResponseType(
                            uiComponentType = UIComponentType.SnackBar(
                                message = CITIES_LOADED_ERROR_MESSAGE
                            ), messageType = MessageType.Error()
                        )
                    )
                )
            }
        }
    }

    fun isDeletePending(cityWeatherEntity: WeatherCityEntity?) {
        val cityUpdated = cityWeatherEntity?.copy(isUpdatePending = true)
        val index = cities.indexOf(cityWeatherEntity)
        cities[index].isUpdatePending = true
        citiesList = cities.filter { !it.isUpdatePending }.toList()
        _mutableMainState.value = ListCitiesViewState(
            isShowingSnackBar = true, error = null, result = citiesList,
            responseType = ResponseType(
                uiComponentType = UIComponentType.SnackBar(
                    message = ITEM_REMOVED,
                    undoCallback = true
                ), messageType = MessageType.Success()
            )
        )

        viewModelScope.launch(Dispatchers.IO) {
            updateCity.updateCity(cityUpdated!!.toDatabase())
        }
    }

    fun favoriteCity(city: WeatherCityEntity) {

        val index = cities.indexOf(city)

        if (city.isFavorite) {
            city.isFavorite = false
            viewModelScope.launch {
                updateCity.updateCity(city.toDatabase())
            }

            cities[index].isFavorite = false

            citiesList = cities.toList()
            _mutableMainState.value?.copy(
                isShowingSnackBar = true, error = null, result = citiesList,
                responseType = ResponseType(
                    uiComponentType = UIComponentType.None(),
                    messageType = MessageType.Success()
                )
            )

        } else {
            city.isFavorite = true

            viewModelScope.launch {
                updateCity.updateCity(city.toDatabase())
            }

            cities[index].isFavorite = true
            citiesList = cities.toList()

            _mutableMainState.value?.copy(
                isShowingSnackBar = true,
                error = null,
                result = citiesList,
                responseType = ResponseType(
                    uiComponentType = UIComponentType.None(),
                    messageType = MessageType.Success()
                )
            )
        }
        return
    }

    fun restoreCity() {
        val index = cities.indexOfFirst { it.isUpdatePending }
        cities[index].isUpdatePending = false
        citiesList = cities.toList()
        val cityUpdated = cities[index]
        _mutableMainState.value =
            ListCitiesViewState(
                isShowingSnackBar = true, error = null, result = citiesList,
                responseType = ResponseType(
                    uiComponentType = UIComponentType.SnackBar(
                        message = CITY_RESTORED_SUCCESSFULLY,
                        undoCallback = false
                    ), messageType = MessageType.Success()
                )
            )

        viewModelScope.launch(Dispatchers.IO) {
            updateCity.updateCity(cityUpdated.toDatabase())
        }
    }

    fun deleteCity() {
        viewModelScope.launch(Dispatchers.IO) {
            val city = getCityPendingDeleteUseCase(Unit)

            if (city != null) {
                if (city.isUpdatePending) {
                    deleteCityUseCase(city.cityId!!)
                }
            }
            val index = cities.indexOfFirst { it.isUpdatePending }
            if (index == -1) {

            } else {
                cities.remove(cities[index])
                citiesList = cities.toList()
                _mutableMainState.postValue(
                    ListCitiesViewState(
                        isShowingSnackBar = true, error = null, result = citiesList,
                        responseType = ResponseType(
                            uiComponentType = UIComponentType.SnackBar(
                                message = ITEM_REMOVED,
                                undoCallback = false
                            ), messageType = MessageType.Success()
                        )
                    )
                )
            }
        }
    }

    fun stopShowingSnackBar() {
        _mutableMainState.value?.copy(isShowingSnackBar = false)
    }

    fun refreshCities(sortOrder: SortOrder) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = refreshCitiesUseCase(cities.toMutableList())

            when (result) {
                is Result.Success -> {
                    sortCities(sortOrder)
                }

                is Result.Failure -> {
                    _mutableMainState.postValue(
                        ListCitiesViewState(
                            isShowingSnackBar = true,
                            error = UPDATE_CITY_ERROR_MESSAGE, result = citiesList,
                            responseType = ResponseType(
                                uiComponentType = UIComponentType.SnackBar(message = UPDATE_CITY_ERROR_MESSAGE),
                                messageType = MessageType.Error()
                            )
                        )
                    )
                }
            }
        }
    }

    fun onSortOrderSelected(sortOrder: SortOrder) = viewModelScope.launch {
        preferences.updateSortOrder(sortOrder)
    }

    fun updateCityId(cityId: Int) = viewModelScope.launch {
        preferences.updateCityId(cityId)
    }

    fun sortCities(sortOrder: SortOrder) {
        when (sortOrder) {
            SortOrder.BY_DATE -> _mutableMainState.postValue(
                ListCitiesViewState(
                    isShowingSnackBar = true,
                    error = null,
                    result = citiesList.sortedByDescending { it.requestTime },
                    responseType = ResponseType(
                        uiComponentType = UIComponentType.None(),
                        messageType = MessageType.Success()
                    )
                )
            )
            SortOrder.BY_NAME -> _mutableMainState.postValue(
                ListCitiesViewState(
                    isShowingSnackBar = true,
                    error = null,
                    result = citiesList.sortedBy { it.cityName },
                    responseType = ResponseType(
                        uiComponentType = UIComponentType.None(),
                        messageType = MessageType.Success()
                    )
                )
            )
            SortOrder.BY_FAVORITE -> _mutableMainState.postValue(
                ListCitiesViewState(
                    isShowingSnackBar = true,
                    error = null,
                    result = citiesList.sortedByDescending { it.isFavorite },
                    responseType = ResponseType(
                        uiComponentType = UIComponentType.None(),
                        messageType = MessageType.Success()
                    )
                )
            )
        }
    }

    fun updateCitySort(sortOrder: SortOrder) = viewModelScope.launch {
        preferences.updateSortOrder(sortOrder)
    }
}


