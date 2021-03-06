package com.ricardoteixeira.app.presentation.listcities

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ricardoteixeira.app.framework.db.mappers.toDatabase
import com.ricardoteixeira.app.utils.PreferencesManager
import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.app.utils.SortOrder
import com.ricardoteixeira.data.repository.InsertCurrentWeatherIntoDatabase
import com.ricardoteixeira.data.repository.WeatherRepository
import com.ricardoteixeira.domain.models.current.CurrentWeatherEntityModel
import com.ricardoteixeira.domain.usecases.futureweather.FetchFutureWeatherByIdUseCase
import com.ricardoteixeira.domain.usecases.futureweather.FetchFutureWeatherByNameUseCase
import com.ricardoteixeira.domain.usecases.listcities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

const val FETCH_CITY_SUCCESS_MESSAGE = "City Added Successfully"
const val FETCH_CITY_ERROR_MESSAGE = "Problem adding city! Try again."

const val CITIES_LOADED_SUCCESS_MESSAGE = "Cities loaded successfully"
const val CITIES_EMPTY_LOADED_SUCCESS_MESSAGE = "No cities in database"
const val CITIES_LOADED_ERROR_MESSAGE = "Problem while loading cities"

const val CITY_RESTORED_SUCCESSFULLY = "City restored successfully"
const val ITEM_PENDING = "1 item removed from the list"

const val ITEM_REMOVED = "Item deleted successfully!"

const val UPDATE_CITY_SUCCESS_MESSAGE = "Cities refreshed successfully"
const val UPDATE_CITY_ERROR_MESSAGE = "Cities updated failed!"

class ListCitiesViewModel
@ViewModelInject constructor(
    var fetchCityByNameFromApiUseCase: FetchCurrentWeatherByNameFromApiUseCase,
    val fetchFutureWeatherByNameUseCase: FetchFutureWeatherByNameUseCase,
    var fetchCityByIdFromApiUseCase: FetchCurrentWeatherByIdFromApiUseCase,
    val fetchFutureWeatherByIdUseCase: FetchFutureWeatherByIdUseCase,
    var insertCityIntoDatabase: InsertCurrentWeatherIntoDatabase,
    var weatherRepository: WeatherRepository,
    var updateCity: UpdateCityUseCase,
    var getCityPendingDeleteUseCase: GetCityPendingDeleteUseCase,
    var deleteCityUseCase: DeleteCityUseCase,
    var refreshCitiesUseCase: RefreshCitiesUseCase,
    val getCitiesByNameUseCase: GetCitiesByNameUseCase,
    private val preferences: PreferencesManager
) : ViewModel() {

    private val _mutableMainState: MutableLiveData<ListCitiesViewState> = MutableLiveData()
    val mainState: LiveData<ListCitiesViewState>
        get() = _mutableMainState

    var citiesArrayList: ArrayList<CurrentWeatherEntityModel> = arrayListOf()
    var citiesList: List<CurrentWeatherEntityModel> = listOf()

    val preferencesFlow = preferences.preferencesFlow.asLiveData()

    val searchQuery = MutableStateFlow("")

    private val citiesFlow = searchQuery.flatMapLatest {

        if (it.isBlank()) {
            getCitiesByNameUseCase("afsfasfafdfafdsdf")
        } else {
            getCitiesByNameUseCase(it)
        }
    }

    val cities = citiesFlow.asLiveData()

    fun fetchWeatherByName(cityName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            citiesList = citiesArrayList.toList()
            fetchFutureWeatherByNameUseCase(cityName)
            when (val data = fetchCityByNameFromApiUseCase(cityName)) {
                is Result.Success -> {
                    citiesArrayList.add(0, data.data)
                    citiesList = citiesArrayList.toList()
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

    fun fetchWeatherById(cityId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            citiesList = citiesArrayList.toList()
            fetchFutureWeatherByIdUseCase(cityId)
            when (val data = fetchCityByIdFromApiUseCase(cityId)) {
                is Result.Success -> {
                    citiesArrayList.add(0, data.data)
                    citiesList = citiesArrayList.toList()
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

    fun getCities(sortOrder: SortOrder) {
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
                        citiesArrayList = arrayListOf()
                        citiesArrayList.addAll(result.data)
                        citiesList = citiesArrayList.toList()
                        when (sortOrder) {
                            SortOrder.BY_DATE -> _mutableMainState.postValue(
                                ListCitiesViewState(
                                    isShowingSnackBar = true,
                                    error = null,
                                    result = citiesList.filter { !it.isUpdatePending }
                                        .sortedByDescending { it.requestTime },
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
                                    result = citiesList.filter { !it.isUpdatePending }
                                        .sortedBy { it.cityName },
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
                                    result = citiesList.filter { !it.isUpdatePending }
                                        .sortedByDescending { it.isFavorite },
                                    responseType = ResponseType(
                                        uiComponentType = UIComponentType.None(),
                                        messageType = MessageType.Success()
                                    )
                                )
                            )
                        }
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
        val index = citiesArrayList.indexOf(cityCurrentWeatherEntityModel)
        citiesArrayList[index].isUpdatePending = true
        citiesList = citiesArrayList.toList()
        _mutableMainState.value = ListCitiesViewState(
            isShowingSnackBar = true,
            error = null,
            result = citiesList.filter { !it.isUpdatePending },
            responseType = ResponseType(
                uiComponentType = UIComponentType.SnackBar(
                    message = ITEM_PENDING,
                    undoCallback = true
                ), messageType = MessageType.Success()
            )
        )

        viewModelScope.launch(Dispatchers.IO) {
            updateCity(cityUpdated!!.toDatabase())
        }
    }

    fun favoriteCity(current: CurrentWeatherEntityModel) {

        val index = citiesArrayList.indexOf(current)

        if (current.isFavorite) {
            current.isFavorite = false
            viewModelScope.launch {
                updateCity(current.toDatabase())
            }

            citiesArrayList[index].isFavorite = false

            citiesList = citiesArrayList.toList()
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
                updateCity(current.toDatabase())
            }

            citiesArrayList[index].isFavorite = true
            citiesList = citiesArrayList.toList()

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
        val index = citiesArrayList.indexOfFirst { it.isUpdatePending }
        citiesArrayList[index].isUpdatePending = false
        val cityUpdated = citiesArrayList[index]
        citiesList = citiesArrayList.toList()

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
            updateCity(cityUpdated.toDatabase())
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
            val index = citiesArrayList.indexOfFirst { it.isUpdatePending }
            if (index != -1) {
                citiesArrayList.remove(citiesArrayList[index])
                citiesList = citiesArrayList.toList()
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

    fun refreshCities(sortOrder: SortOrder) {
        viewModelScope.launch(Dispatchers.IO) {

            when (refreshCitiesUseCase(citiesArrayList.toMutableList())) {
                is Result.Success -> {
                    getCities(sortOrder)
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

    fun updateCitySort(sortOrder: SortOrder) = viewModelScope.launch {
        preferences.updateSortOrder(sortOrder)
    }

}


