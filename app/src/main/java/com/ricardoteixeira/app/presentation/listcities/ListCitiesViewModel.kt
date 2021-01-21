package com.ricardoteixeira.app.presentation.listcities

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ricardoteixeira.app.framework.db.mappers.toDatabase
import com.ricardoteixeira.app.utils.PreferencesManager
import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.app.utils.SortOrder
import com.ricardoteixeira.data.repository.InsertCityIntoDatabase
import com.ricardoteixeira.data.repository.UpdateCity
import com.ricardoteixeira.data.repository.WeatherRepository
import com.ricardoteixeira.domain.models.current.CurrentWeatherEntityModel
import com.ricardoteixeira.domain.usecases.futureweather.FetchFutureWeatherUseCase
import com.ricardoteixeira.domain.usecases.listcities.DeleteCityUseCase
import com.ricardoteixeira.domain.usecases.listcities.FetchCityUseCase
import com.ricardoteixeira.domain.usecases.listcities.GetCityPendingDeleteUseCase
import com.ricardoteixeira.domain.usecases.listcities.RefreshCitiesUseCase
import kotlinx.coroutines.Dispatchers
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
    val fetchFutureWeatherUseCase: FetchFutureWeatherUseCase,
    private val preferences: PreferencesManager
) : ViewModel() {

    private val _mutableMainState: MutableLiveData<ListCitiesViewState> = MutableLiveData()
    val mainState: LiveData<ListCitiesViewState>
        get() = _mutableMainState

    var currents: ArrayList<CurrentWeatherEntityModel> = arrayListOf()
    var citiesList: List<CurrentWeatherEntityModel> = listOf()

    val preferencesFlow = preferences.preferencesFlow.asLiveData()


    fun fetchCity(cityName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            citiesList = currents.toList()
            val teste = fetchFutureWeatherUseCase(cityName)
            when(teste) {
                is Result.Success -> println("testeeeeeeeviewmodel ${teste.data}")
                is Result.Failure -> println("testeeeeeeeviewmodel ${teste.error}")
            }
            when (val data = fetchCityUseCase(cityName)) {
                is Result.Success -> {
                    currents.add(0, data.data)
                    citiesList = currents.toList()
                        _mutableMainState.postValue(
                            ListCitiesViewState(
                            isShowingSnackBar = true, error = null, result = citiesList,
                            responseType = ResponseType(
                                uiComponentType = UIComponentType.SnackBar(
                                    message = FETCH_CITY_SUCCESS_MESSAGE,
                                    undoCallback = false
                                ), messageType = MessageType.Success()
                            )
                        )
                        )
                }

                is Result.Failure -> _mutableMainState.postValue(
                    ListCitiesViewState(
                        isShowingSnackBar = true,
                        result = citiesList,
                        error = data.error,
                        responseType = ResponseType(
                            uiComponentType = UIComponentType.SnackBar(message = FETCH_CITY_ERROR_MESSAGE),
                            messageType = MessageType.Error()
                        )
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
                                result = listOf<CurrentWeatherEntityModel>(),
                                responseType = ResponseType(
                                    uiComponentType = UIComponentType.SnackBar(message = CITIES_EMPTY_LOADED_SUCCESS_MESSAGE),
                                    messageType = MessageType.Success()
                                )
                            )
                        )
                    } else {
                        currents = arrayListOf()
                        currents.addAll(result.data.filter { !it.isUpdatePending })
                        citiesList = currents.toList()
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
                        result = listOf<CurrentWeatherEntityModel>(),
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

    fun isDeletePending(cityCurrentWeatherEntityModel: CurrentWeatherEntityModel?) {
        val cityUpdated = cityCurrentWeatherEntityModel?.copy(isUpdatePending = true)
        val index = currents.indexOf(cityCurrentWeatherEntityModel)
        currents[index].isUpdatePending = true
        citiesList = currents.filter { !it.isUpdatePending }.toList()
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

    fun favoriteCity(current: CurrentWeatherEntityModel) {

        val index = currents.indexOf(current)

        if (current.isFavorite) {
            current.isFavorite = false
            viewModelScope.launch {
                updateCity.updateCity(current.toDatabase())
            }

            currents[index].isFavorite = false

            citiesList = currents.toList()
            _mutableMainState.value?.copy(
                isShowingSnackBar = true, error = null, result = citiesList,
                responseType = ResponseType(
                    uiComponentType = UIComponentType.None(),
                    messageType = MessageType.Success()
                )
            )

        } else {
            current.isFavorite = true

            viewModelScope.launch {
                updateCity.updateCity(current.toDatabase())
            }

            currents[index].isFavorite = true
            citiesList = currents.toList()

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
        val index = currents.indexOfFirst { it.isUpdatePending }
        currents[index].isUpdatePending = false
        citiesList = currents.toList()
        val cityUpdated = currents[index]
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
            val index = currents.indexOfFirst { it.isUpdatePending }
            if (index != -1) {
                currents.remove(currents[index])
                citiesList = currents.toList()
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
            val result = refreshCitiesUseCase(currents.toMutableList())

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


