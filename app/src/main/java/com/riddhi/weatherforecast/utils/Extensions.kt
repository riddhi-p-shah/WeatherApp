package com.riddhi.weatherforecast.utils

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.riddhi.weatherforecast.utils.Constants.PREFERENCES_NAME

// Extension property for Context that provides access to the shared preferences data store.
val Context.dataStore by preferencesDataStore(PREFERENCES_NAME)
