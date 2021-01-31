package com.ricardoteixeira.di

import android.app.Application
import androidx.room.Room
import com.ricardoteixeira.app.framework.db.WeatherCityDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application) =
        Room.databaseBuilder(app, WeatherCityDatabase::class.java, "weather_city_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideWeatherCityDao(database: WeatherCityDatabase) = database.weatherCityDao()

    @Provides
    fun provideFutureWeatherDao(database: WeatherCityDatabase) = database.futureWeatherDao()

    @Provides
    fun provideCityDao(database: WeatherCityDatabase) = database.cityDao()
}