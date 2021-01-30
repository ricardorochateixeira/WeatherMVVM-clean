package com.ricardoteixeira.di

import com.ricardoteixeira.app.framework.api.ApiHelper
import com.ricardoteixeira.app.framework.api.ApiHelperImpl
import com.ricardoteixeira.app.framework.repository.details.GetCityByIdImpl
import com.ricardoteixeira.app.framework.repository.favorites.GetFavoriteCitiesImpl
import com.ricardoteixeira.app.framework.repository.future.FetchFutureWeatherByIdFromApiImpl
import com.ricardoteixeira.app.framework.repository.future.FetchFutureWeatherByNameFromApiImpl
import com.ricardoteixeira.app.framework.repository.future.GetFutureWeatherFromDatabaseImpl
import com.ricardoteixeira.app.framework.repository.future.InsertFutureWeatherIntoDatabaseImpl
import com.ricardoteixeira.app.framework.repository.listcities.*
import com.ricardoteixeira.app.framework.repository.splash.InsertCityInformationIntoDatabaseImpl
import com.ricardoteixeira.data.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class UseCasesModule {

    @Binds
    abstract fun bindInsertNewCity(insertNewCity: InsertNewCityImpl): InsertNewCity

    @Binds
    abstract fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper

    @Binds
    abstract fun provideInsertFromApi(insertCityFromApi: FetchCityByNameFromApiImpl): FetchCityByNameFromApi

    @Binds
    abstract fun provideInsertFutureFromApi(insertFutureWeatherFromApi: FetchFutureWeatherByNameFromApiImpl): FetchFutureWeatherByNameFromApi

    @Binds
    abstract fun provideInsertByIdFromApi(insertCityFromApi: FetchCityByIdFromApiImpl): FetchCityByIdFromApi

    @Binds
    abstract fun provideInsertByIdFutureFromApi(fetchFutureWeatherFromApi: FetchFutureWeatherByIdFromApiImpl): FetchFutureWeatherByIdFromApi

    @Binds
    abstract fun provideInsertCurrentWeatherIntoDatabase(insertCurrentWeatherIntoDatabase: InsertCurrentWeatherIntoDatabaseImpl): InsertCurrentWeatherIntoDatabase

    @Binds
    abstract fun provideInsertFutureWeatherIntoDatabase(insertFutureWeatherIntoDatabase: InsertFutureWeatherIntoDatabaseImpl): InsertFutureWeatherIntoDatabase

    @Binds
    abstract fun provideGetAllCities(getAllCities: GetAllCitiesImpl): GetAllCities

    @Binds
    abstract fun deleteCityFromDatabase(getAllCities: DeleteCityFromDatabaseImpl): DeleteCityFromDatabase

    @Binds
    abstract fun updateCity(updateCity: UpdateCityImpl): UpdateCity

    @Binds
    abstract fun getCityPendingDelete(getCityPendingDelete: GetCityPendingDeleteImpl): GetCityPendingDelete

    @Binds
    abstract fun refreshCitiesUseCase(refreshCities: RefreshCitiesImpl): RefreshCities

    @Binds
    abstract fun getCityByIdUseCase(getCityById: GetCityByIdImpl): GetCityById

    @Binds
    abstract fun getFavoriteCities(getFavoriteCities: GetFavoriteCitiesImpl): GetFavoriteCities

    @Binds
    abstract fun getFutureWeatherFromDatabase(getFutureWeatherFromDatabase: GetFutureWeatherFromDatabaseImpl): GetFutureWeatherFromDatabase

    @Binds
    abstract fun insertCityInformationIntoDatabase(insertCityInformationIntoDatabase: InsertCityInformationIntoDatabaseImpl): InsertCityInformationIntoDatabase

    @Binds
    abstract fun getCitiesByName(getCitiesByName: GetCitiesByNameImpl): GetCitiesByName

}