package com.riddhi.weatherforecast

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * This is the main application class for the Weather application.
 * It uses Hilt for dependency injection
 */
@HiltAndroidApp
class WeatherApplication:Application() {
}