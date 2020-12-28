package com.ricardoteixeira.di

import com.ricardoteixeira.app.framework.api.WeatherService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


const val BASE_URL = "https://api.openweathermap.org/"

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .addInterceptor(addInterceptor())
                .build()

    }

    @Provides
    @Singleton
    fun provideMoshiBuilder(): Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(provideMoshiBuilder()).asLenient())
            .build()

    @Provides
    @Singleton
    fun addInterceptor() = Interceptor { chain ->
        val url = chain.request()
                .url
                .newBuilder()
                .addQueryParameter("appid", "5586edf09c94c8895b3c5ade2f3a2048")
                .build()

        val request = chain.request()
                .newBuilder()
                .url(url)
                .build()
        return@Interceptor chain.proceed(request)
    }

    @Provides
    @Singleton
    fun provideWeatherService(retrofit: Retrofit): WeatherService = retrofit.create(WeatherService::class.java)


}
