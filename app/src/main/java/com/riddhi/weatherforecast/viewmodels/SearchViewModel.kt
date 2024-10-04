package com.riddhi.weatherforecast.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riddhi.weatherforecast.api.ApiState
import com.riddhi.weatherforecast.datastore.DataStore
import com.riddhi.weatherforecast.models.GeoCodeModel
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
 *  SearchViewModel is responsible for handling search-related operations in the weather forecast app.
 *
 * @property weatherForecastRepository Repository for interacting with the weather API and search functionalities.
 * @property dataStore DataStore instance for storing user location preferences.
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val weatherForecastRepository: WeatherForecastRepository,
    private val dataStore: DataStore
) :
    ViewModel() {

    /**
     * Mutable state flow representing the current state of the search API request.
     * Emits ApiStates to indicate loading, success, or error states.
     */
    private val _geoApiState = MutableStateFlow<ApiState>(ApiState.Loading)
    val geoApiState: StateFlow<ApiState> = _geoApiState.asStateFlow()

    /**
     * Fetches suggestions for locations based on the provided search query.
     * Only fetches suggestions if the query is not empty.
     *
     * @param query The user's search query for a location.
     */
    fun getSuggestions(query: String) {
        if (query.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    _geoApiState.value = ApiState.Loading
                    weatherForecastRepository.getSuggestions(query)
                    _geoApiState.value = ApiState.Success(weatherForecastRepository.suggestions.value)
                }catch (e: Exception){
                     _geoApiState.value = when (e) {
                         is HttpException -> ApiState.Error("API error: ${e.message}")
                         is IOException -> ApiState.Error("Network error: ${e.message}")
                         else -> ApiState.Error("Something went wrong: ${e.message}")
                     }
                }
            }
        }
    }

    /**
     * Saves the selected location's latitude and longitude to the DataStore for future use.
     *
     * @param geoCodeModel The GeoCodeModel object containing the location information.
     */
    fun saveLocation(geoCodeModel: GeoCodeModel) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.putDouble(Constants.LAT, geoCodeModel.lat!!)
            dataStore.putDouble(Constants.LON, geoCodeModel.lon!!)
        }
    }

}