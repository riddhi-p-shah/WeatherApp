package com.riddhi.weatherforecast.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.riddhi.weatherforecast.datastore.DataStore
import com.riddhi.weatherforecast.repository.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * Dagger module for providing location-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
class LocationModule {

    /**
     * Provides an instance of FusedLocationProviderClient for accessing location services.
     *
     * @param appContext The application context.
     * @return A new instance of FusedLocationProviderClient.
     */
    @Provides
    fun providesLocationClient(
        @ApplicationContext appContext: Context
    ): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(appContext)
    }

}