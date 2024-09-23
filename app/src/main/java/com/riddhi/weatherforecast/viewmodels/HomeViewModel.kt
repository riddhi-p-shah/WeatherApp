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

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherForecastRepository: WeatherForecastRepository,
    private val dataStore: DataStore, private val fusedLocationProviderClient: FusedLocationProviderClient
) : ViewModel() {

    private val _weatherApiState = MutableStateFlow<ApiState>(ApiState.Loading)
    val weatherApiState: StateFlow<ApiState> = _weatherApiState.asStateFlow()

    var permissionGranted: Boolean = false
    private var locationPair: Pair<Double, Double>? = null

    val message = MutableStateFlow<String?>(null)

    fun getWeather() {
        viewModelScope.launch(Dispatchers.IO) {

            val lat = dataStore.getDouble(Constants.LAT)
            val lon = dataStore.getDouble(Constants.LON)

            //if there's no data in preference, then only.. it will search for the current location
            if (lat != null && lon != null) {
                callWeatherApi(lat, lon)
            } else {
                locationPair?.let {
                    callWeatherApi(lat= it.first, lon = it.second)
                }
            }
        }
    }

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