package com.riddhi.weatherforecast.models

import com.google.gson.annotations.SerializedName

/**
 * Data class representing the weather response object obtained from the weather API.
 */
data class WeatherResponseModel(

    /**
     * List containing weather information (e.g., description, icon).
     */
    @SerializedName("weather") var weather: ArrayList<Weather>,

    /**
     * Information about temperature, pressure, humidity, etc.
     */
    @SerializedName("main") var main: Main?,

    /**
     * Information about wind speed.
     */
    @SerializedName("wind") var wind: Wind? = Wind(),

    /**
     * Information about rainfall amount in the last hour
     */
    @SerializedName("rain") var rain: Rain? = Rain(),

    /**
     * Information about snowfall amount in the last hour
     */
    @SerializedName("snow") var snow: Snow? = Snow(),

    /**
     * Information about snowfall amount in the last hour
     */
    @SerializedName("sys") var sys: Sys? = Sys(),

    /**
     * Timezone offset in seconds
     */
    @SerializedName("timezone") var timezone: Int? = null,

    /**
     * Id of the location
     */
    @SerializedName("id") var id: Int? = null,

    /**
     * Location name
     */
    @SerializedName("name") var name: String? = null,

    /**
     * Error code
     */
    @SerializedName("cod") var cod: Int? = null
)

data class Weather(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("main") var main: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("icon") var icon: String? = null

) {
    val iconUrl: String
        get() = "https://openweathermap.org/img/wn/$icon@4x.png"

}

data class Main(

    @SerializedName("temp") var temp: Double? = null,
    @SerializedName("feels_like") var feelsLike: Double? = null,
    @SerializedName("temp_min") var tempMin: Double? = null,
    @SerializedName("temp_max") var tempMax: Double? = null,
    @SerializedName("humidity") var humidity: Int? = null,

)

data class Wind(

    @SerializedName("speed") var speed: Double? = null,

)

data class Rain(

    @SerializedName("1h") var rain: Double? = null

)

data class Snow(

    @SerializedName("1h") var snow: Double? = null

)

data class Sys(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("sunrise") var sunrise: Long? = null,
    @SerializedName("sunset") var sunset: Long? = null

)