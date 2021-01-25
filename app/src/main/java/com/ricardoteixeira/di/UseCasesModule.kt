package com.ricardoteixeira.di

import com.ricardoteixeira.app.framework.api.ApiHelper
import com.ricardoteixeira.app.framework.api.ApiHelperImpl
import com.ricardoteixeira.app.framework.repository.details.GetCityByIdImpl
import com.ricardoteixeira.app.framework.repository.favorites.GetFavoriteCitiesImpl
import com.ricardoteixeira.app.framework.repository.future.FetchFutureWeatherFromApiImpl
import com.ricardoteixeira.app.framework.repository.future.GetFutureWeatherFromDatabaseImpl
import com.ricardoteixeira.app.framework.repository.listcities.*
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
    abstract fun provideInsertFromApi(insertCityFromApi: FetchCityFromApiImpl): FetchCityFromApi

    @Binds
    abstract fun provideInsertFutureFromApi(insertFutureWeatherFromApi: FetchFutureWeatherFromApiImpl): FetchFutureWeatherFromApi

    @Binds
    abstract fun provideInsertCityIntoDatabase(insertCityIntoDatabase: InsertCityIntoDatabaseImpl): InsertCityIntoDatabase

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

}