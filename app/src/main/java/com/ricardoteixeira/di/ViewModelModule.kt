package com.ricardoteixeira.di

import com.ricardoteixeira.data.repository.*
import com.ricardoteixeira.domain.usecases.details.GetCityByIdUseCase
import com.ricardoteixeira.domain.usecases.favorite.GetFavoriteCitiesUseCase
import com.ricardoteixeira.domain.usecases.futureweather.FetchFutureWeatherByIdUseCase
import com.ricardoteixeira.domain.usecases.futureweather.FetchFutureWeatherByNameUseCase
import com.ricardoteixeira.domain.usecases.futureweather.GetFutureWeatherFromDatabaseUseCase
import com.ricardoteixeira.domain.usecases.listcities.*
import com.ricardoteixeira.domain.usecases.splash.InsertCityInformationIntoDatabaseUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object ViewModelModule {

    @Provides
    fun provideInsertCityUseCase(fetchCityFromApi: FetchCityByNameFromApi): FetchCurrentWeatherByNameFromApiUseCase = FetchCurrentWeatherByNameFromApiUseCase(fetchCityFromApi)

    @Provides
    fun provideInsertFutureCityUseCase(fetchFutureWeatherFromApi: FetchFutureWeatherByNameFromApi): FetchFutureWeatherByNameUseCase = FetchFutureWeatherByNameUseCase(fetchFutureWeatherFromApi)

    @Provides
    fun provideInsertCityByIdUseCase(fetchCityFromApi: FetchCityByIdFromApi): FetchCurrentWeatherByIdFromApiUseCase = FetchCurrentWeatherByIdFromApiUseCase(fetchCityFromApi)

    @Provides
    fun provideInsertFutureCityByIdUseCase(fetchFutureWeatherFromApi: FetchFutureWeatherByIdFromApi): FetchFutureWeatherByIdUseCase = FetchFutureWeatherByIdUseCase(fetchFutureWeatherFromApi)

    @Provides
    fun provideInsertCityIntoDatabase(insertCityIntoDatabase: InsertCurrentWeatherIntoDatabase): InsertCityIntoDatabaseUseCase = InsertCityIntoDatabaseUseCase(insertCityIntoDatabase)

    @Provides
    fun provideWeatherRepository(getAllCities: GetAllCities,
                                  fetchCityByIdFromApi: FetchCityByIdFromApi,
                                 fetchFutureWeatherByIdFromApi: FetchFutureWeatherByIdFromApi,
                                 insertCityIntoDatabase: InsertCurrentWeatherIntoDatabase,
                                 insertFutureWeatherIntoDatabase: InsertFutureWeatherIntoDatabase):WeatherRepository =
        WeatherRepository(getAllCities, fetchCityByIdFromApi, fetchFutureWeatherByIdFromApi, insertCityIntoDatabase, insertFutureWeatherIntoDatabase)

    @Provides
    fun providesDeleteCityFromDatabase(deleteCityFromDatabase: DeleteCityFromDatabase): DeleteCityUseCase = DeleteCityUseCase(deleteCityFromDatabase)

    @Provides
    fun providesUpdateCity(updateCity: UpdateCity): UpdateCityUseCase = UpdateCityUseCase(updateCity)

    @Provides
    fun providesGetCityPendingDelete(getCityPendingDelete: GetCityPendingDelete): GetCityPendingDeleteUseCase = GetCityPendingDeleteUseCase(getCityPendingDelete)

    @Provides
    fun providesRefreshCities(refreshCities: RefreshCities): RefreshCitiesUseCase = RefreshCitiesUseCase(refreshCities)

    @Provides
    fun providesGetCityById(getCityById: GetCityById): GetCityByIdUseCase = GetCityByIdUseCase(getCityById)

    @Provides
    fun providesGetFavoriteCities(getFavoriteCities: GetFavoriteCities): GetFavoriteCitiesUseCase = GetFavoriteCitiesUseCase(getFavoriteCities)

    @Provides
    fun provideGetFutureWeatherFromDatabase(getFutureWeatherFromDatabase: GetFutureWeatherFromDatabase): GetFutureWeatherFromDatabaseUseCase = GetFutureWeatherFromDatabaseUseCase(getFutureWeatherFromDatabase)

    @Provides
    fun provideInsertCityInformationUseCase(insertCityInformationIntoDatabase: InsertCityInformationIntoDatabase): InsertCityInformationIntoDatabaseUseCase = InsertCityInformationIntoDatabaseUseCase(insertCityInformationIntoDatabase)

    @Provides
    fun providesGetCitiesByNameUseCase(getCitiesByName: GetCitiesByName): GetCitiesByNameUseCase = GetCitiesByNameUseCase(getCitiesByName)

}