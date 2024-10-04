package com.riddhi.weatherforecast.di

import com.riddhi.weatherforecast.api.WeatherApi
import com.riddhi.weatherforecast.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Dagger module for providing network-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    /**
     * Provides a singleton instance of Retrofit for making network requests.
     *
     * @return A new instance of Retrofit configured with the base URL and Gson converter.
     */
    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    /**
     * Provides an instance of WeatherApi using the provided Retrofit object.
     *
     * @param retrofit The instance of Retrofit used for creating the API interface.
     * @return A new instance of WeatherApi.
     */
    @Singleton
    @Provides
    fun providesWeatherApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

}