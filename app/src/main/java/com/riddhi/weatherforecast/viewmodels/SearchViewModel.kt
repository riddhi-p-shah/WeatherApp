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

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val weatherForecastRepository: WeatherForecastRepository,
    private val dataStore: DataStore
) :
    ViewModel() {

    private val _geoApiState = MutableStateFlow<ApiState>(ApiState.Loading)
    val geoApiState: StateFlow<ApiState> = _geoApiState.asStateFlow()

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

    fun saveLocation(geoCodeModel: GeoCodeModel) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.putDouble(Constants.LAT, geoCodeModel.lat!!)
            dataStore.putDouble(Constants.LON, geoCodeModel.lon!!)
        }
    }

}