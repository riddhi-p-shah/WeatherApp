package com.riddhi.weatherforecast.api

/**
 * Sealed class representing the different states of an API request.
 */
sealed class ApiState {

    /**
     * Represents the loading state of the API request.
     */
    object Loading : ApiState()

    /**
     * Represents the success state of the API request, containing the retrieved data.
     *
     * @param data The retrieved data of type T.
     */
    data class Success<T>(val data: T) : ApiState()

    /**
     * Represents the error state of the API request, containing an error message.
     *
     * @param message The error message.
     */
    data class Error(val message: String) : ApiState()
}


