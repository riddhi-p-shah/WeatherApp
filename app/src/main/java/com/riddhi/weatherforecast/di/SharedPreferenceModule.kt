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

@Module
@InstallIn(SingletonComponent::class)
class SharedPreferenceModule {

    @Singleton
    @Provides
    fun provideDataStoreRepository(
        @ApplicationContext appContext: Context
    ): DataStore = DataStoreRepository(appContext)


}