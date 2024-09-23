package com.riddhi.weatherforecast.utils

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore


object Constants {

    //preferences
    const val PREFERENCES_NAME = "my_preferences"

    //networking
    const val API_KEY = "3ee5c11154033ede4acf51a6dbb59af2"
    const val BASE_URL = "https://api.openweathermap.org/"

    //navigation
    const val HOME_SCREEN = "home"
    const val SEARCH_SCREEN = "search"

    //nav args
    const val SELECTED_LOCATION = "selected_location"

    //datastore
    const val LAT = "lat"
    const val LON = "lon"

}