package com.ricardoteixeira.di

import com.ricardoteixeira.data.repository.*
import com.ricardoteixeira.domain.usecases.details.GetCityByIdUseCase
import com.ricardoteixeira.domain.usecases.listcities.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object ViewModelModule {

    @Provides
    fun provideInsertCityUseCase(fetchCityFromApi: FetchCityFromApi): FetchCityUseCase = FetchCityUseCase(fetchCityFromApi)

    @Provides
    fun provideInsertCityIntoDatabase(insertCityIntoDatabase: InsertCityIntoDatabase): InsertCityIntoDatabaseUseCase = InsertCityIntoDatabaseUseCase(insertCityIntoDatabase)

    @Provides
    fun provideWeatherRepository(getAllCities: GetAllCities, fetchCityFromApi: FetchCityFromApi, insertCityIntoDatabase:InsertCityIntoDatabase):WeatherRepository =  WeatherRepository(getAllCities, fetchCityFromApi,insertCityIntoDatabase)

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
}