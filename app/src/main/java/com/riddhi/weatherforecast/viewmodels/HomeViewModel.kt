package com.riddhi.weatherforecast.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.riddhi.weatherforecast.api.ApiState
import com.riddhi.weatherforecast.datastore.DataStore
import com.riddhi.weatherforecast.models.WeatherResponseModel
import com.riddhi.weatherforecast.repository.WeatherForecastRepository
import com.riddhi.weatherforecast.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * ViewModel class responsible for fetching and managing weather data for the Home screen.
 *
 * @property weatherForecastRepository Repository for interacting with the weather API.
 * @property dataStore DataStore instance for accessing stored user preferences.
 * @property fusedLocationProviderClient class for accessing the user's location.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherForecastRepository: WeatherForecastRepository,
    private val dataStore: DataStore, private val fusedLocationProviderClient: FusedLocationProviderClient
) : ViewModel() {

    /**
     * Mutable state flow representing the current state of the weather API request.
     * Emits ApiStates to indicate loading, success, or error states.
     */
    private val _weatherApiState = MutableStateFlow<ApiState>(ApiState.Loading)
    val weatherApiState: StateFlow<ApiState> = _weatherApiState.asStateFlow()

    /**
     * Boolean flag indicating whether location permission has been granted.
     */
    var permissionGranted: Boolean = false

    /**
     * A pair of Double values representing the user's latitude and longitude
     */
    private var locationPair: Pair<Double, Double>? = null

    /**
     * Mutable state flow to hold an error message
     */
    val message = MutableStateFlow<String?>(null)

    /**
     * Fetches weather data based on the user's location or stored preferences.
     * If no location data is available in preferences, it attempts to retrieve the user's current location.
     */
    fun getWeather() {
        viewModelScope.launch(Dispatchers.IO) {

            val lat = dataStore.getDouble(Constants.LAT)
            val lon = dataStore.getDouble(Constants.LON)

            // Prioritize using stored location data if available
            if (lat != null && lon != null) {
                callWeatherApi(lat, lon)
            } else {
                // If no stored location, try to get user's current location
                locationPair?.let {
                    callWeatherApi(lat= it.first, lon = it.second)
                }
            }
        }
    }

    /**
     * Makes an API call to retrieve weather data for the given latitude and longitude.
     * Updates the weather state and potential error messages within a coroutine.
     *
     * @param lat The user's latitude.
     * @param lon The user's longitude.
     */
    private fun callWeatherApi(lat: Double, lon: Double){
        viewModelScope.launch {
            try {
                _weatherApiState.value = ApiState.Loading
                weatherForecastRepository.getWeather(lat = lat, lon = lon)
                _weatherApiState.value = ApiState.Success(weatherForecastRepository.weatherData.value)
            }catch (e: Exception){
                _weatherApiState.value =
                    when (e) {
                        is HttpException -> ApiState.Error("API error: ${e.message}")
                        is IOException -> ApiState.Error("Network error: ${e.message}")
                        else -> ApiState.Error("Something went wrong: ${e.message}")
                    }
            }
        }
    }

    /**
     * Attempts to retrieve the user's last known location if location permissions are granted.
     */
    @SuppressLint("MissingPermission")
    fun getLastUserLocation() {
        // Check if location permissions are granted
        if (permissionGranted) {
            // Retrieve the last known location
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location ->
                    location?.let {
                        locationPair = Pair(it.latitude, it.longitude)
                        getWeather()
                    }
                }
                .addOnFailureListener { exception ->
                    message.value = exception.message
                }
        }
    }
}