package com.riddhi.weatherforecast.models

import com.google.gson.annotations.SerializedName

data class WeatherResponseModel(

    @SerializedName("coord") var coord: Coord?,
    @SerializedName("weather") var weather: ArrayList<Weather>,
    @SerializedName("main") var main: Main?,
    @SerializedName("wind") var wind: Wind? = Wind(),
    @SerializedName("rain") var rain: Rain? = Rain(),
    @SerializedName("snow") var snow: Snow? = Snow(),
    @SerializedName("sys") var sys: Sys? = Sys(),
    @SerializedName("timezone") var timezone: Int? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("cod") var cod: Int? = null
)

data class Coord (

    @SerializedName("lon" ) var lon : Double? = null,
    @SerializedName("lat" ) var lat : Double? = null

)

data class Weather (

    @SerializedName("id"          ) var id          : Int?    = null,
    @SerializedName("main"        ) var main        : String? = null,
    @SerializedName("description" ) var description : String? = null,
    @SerializedName("icon"        ) var icon        : String? = null

){
    val iconUrl: String
        get() = "https://openweathermap.org/img/wn/$icon@4x.png"

}

data class Main (

    @SerializedName("temp"       ) var temp      : Double? = null,
    @SerializedName("feels_like" ) var feelsLike : Double? = null,
    @SerializedName("temp_min"   ) var tempMin   : Double? = null,
    @SerializedName("temp_max"   ) var tempMax   : Double? = null,
    @SerializedName("pressure"   ) var pressure  : Int?    = null,
    @SerializedName("humidity"   ) var humidity  : Int?    = null,
    @SerializedName("sea_level"  ) var seaLevel  : Int?    = null,
    @SerializedName("grnd_level" ) var grndLevel : Int?    = null

)

data class Wind (

    @SerializedName("speed" ) var speed : Double? = null,
    @SerializedName("deg"   ) var deg   : Int?    = null

)
data class Rain (

    @SerializedName("1h" ) var rain : Double? = null

)

data class Snow (

    @SerializedName("1h" ) var snow : Double? = null

)

data class Sys (

    @SerializedName("type"    ) var type    : Int?    = null,
    @SerializedName("id"      ) var id      : Int?    = null,
    @SerializedName("country" ) var country : String? = null,
    @SerializedName("sunrise" ) var sunrise : Long?    = null,
    @SerializedName("sunset"  ) var sunset  : Long?    = null

)