package com.riddhi.weatherforecast.api

sealed class ApiState {
    object Loading : ApiState()
    data class Success<T>(val data: T) : ApiState()
    data class Error(val message: String) : ApiState()

}


