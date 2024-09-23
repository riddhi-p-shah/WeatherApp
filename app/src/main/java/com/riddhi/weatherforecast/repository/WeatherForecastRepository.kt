package com.riddhi.weatherforecast.repository

import com.riddhi.weatherforecast.api.WeatherApi
import com.riddhi.weatherforecast.models.GeoCodeModel
import com.riddhi.weatherforecast.models.WeatherResponseModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class WeatherForecastRepository @Inject constructor(private val weatherApi: WeatherApi) {

    private val _suggestions = MutableStateFlow<List<GeoCodeModel>>(emptyList())
    val suggestions: StateFlow<List<GeoCodeModel>>
        get() = _suggestions

    private val _weatherData = MutableStateFlow<WeatherResponseModel?>(null)
    val weatherData: StateFlow<WeatherResponseModel?>
        get() = _weatherData


    suspend fun getSuggestions(query: String) {
        val response = weatherApi.getGeoCode(query = query)

        if (response.isSuccessful && response.body() != null) {
            _suggestions.emit(response.body()!!)
        }
    }

    suspend fun getWeather(lat: Double, lon: Double) {
        val response = weatherApi.getWeather(lat = lat, lon = lon)

        if(response.isSuccessful && response.body() != null){
            _weatherData.emit(response.body()!!)
        }
    }

}