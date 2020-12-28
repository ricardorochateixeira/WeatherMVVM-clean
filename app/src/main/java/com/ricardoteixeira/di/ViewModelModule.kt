package com.ricardoteixeira.di

import com.ricardoteixeira.data.remote.repository.*
import com.ricardoteixeira.domain.usecases.*
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
}