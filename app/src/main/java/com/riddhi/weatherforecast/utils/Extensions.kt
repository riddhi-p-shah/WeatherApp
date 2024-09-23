package com.riddhi.weatherforecast.utils

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.riddhi.weatherforecast.utils.Constants.PREFERENCES_NAME

val Context.dataStore by preferencesDataStore(PREFERENCES_NAME)

