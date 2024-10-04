package com.riddhi.weatherforecast.di

import android.content.Context
import com.riddhi.weatherforecast.datastore.DataStore
import com.riddhi.weatherforecast.repository.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger module for providing shared preference-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
class SharedPreferenceModule {

    /**
     * Provides a singleton instance of DataStoreRepository for interacting with shared preferences.
     *
     * @param appContext The application context.
     * @return A new instance of DataStoreRepository.
     */
    @Singleton
    @Provides
    fun provideDataStoreRepository(
        @ApplicationContext appContext: Context
    ): DataStore = DataStoreRepository(appContext)


}