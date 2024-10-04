package com.riddhi.weatherforecast.repository

import com.riddhi.weatherforecast.api.WeatherApi
import com.riddhi.weatherforecast.models.GeoCodeModel
import com.riddhi.weatherforecast.models.WeatherResponseModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * Repository class for fetching geocode and weather data.
 */
class WeatherForecastRepository @Inject constructor(private val weatherApi: WeatherApi) {

    /**
     * StateFlow containing the list of suggested locations.
     */
    private val _suggestions = MutableStateFlow<List<GeoCodeModel>>(emptyList())
    val suggestions: StateFlow<List<GeoCodeModel>>
        get() = _suggestions

    /**
     * StateFlow containing the current weather data.
     */
    private val _weatherData = MutableStateFlow<WeatherResponseModel?>(null)
    val weatherData: StateFlow<WeatherResponseModel?>
        get() = _weatherData


    /**
     * Fetches suggestions for locations based on a given query.
     *
     * @param query The search query for the location.
     */
    suspend fun getSuggestions(query: String) {
        val response = weatherApi.getGeoCode(query = query)

        if (response.isSuccessful && response.body() != null) {
            _suggestions.emit(response.body()!!)
        }
    }

    /**
     * Fetches weather data for a given latitude and longitude.
     *
     * @param lat The latitude coordinate.
     * @param lon The longitude coordinate.
     */
    suspend fun getWeather(lat: Double, lon: Double) {
        val response = weatherApi.getWeather(lat = lat, lon = lon)

        if(response.isSuccessful && response.body() != null){
            _weatherData.emit(response.body()!!)
        }
    }

}