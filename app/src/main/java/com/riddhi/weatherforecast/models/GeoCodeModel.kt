package com.riddhi.weatherforecast.models

import com.google.gson.annotations.SerializedName

/**
 * Data class representing a location retrieved from the geocode API.
 */
data class GeoCodeModel(

    /**
     * The name of the location.
     */
    @SerializedName("name") var name: String? = null,

    /**
     * The latitude coordinate of the location.
     */
    @SerializedName("lat") var lat: Double? = null,

    /**
     * The longitude coordinate of the location.
     */
    @SerializedName("lon") var lon: Double? = null,

    /**
     * The country code of the location.
     */
    @SerializedName("country") var country: String? = null,

    /**
     * The state of the location.
     */
    @SerializedName("state") var state: String? = null

)