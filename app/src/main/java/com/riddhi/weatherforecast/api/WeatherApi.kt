package com.riddhi.weatherforecast.api

import com.riddhi.weatherforecast.models.GeoCodeModel
import com.riddhi.weatherforecast.models.WeatherResponseModel
import com.riddhi.weatherforecast.utils.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("geo/1.0/direct")
    suspend fun getGeoCode(
        @Query("q") query: String,
        @Query("limit") limit: Int = 10,
        @Query("appid") appid: String = API_KEY
    ): Response<List<GeoCodeModel>>

    //if given more time.. I would use enums for units and allow an option to users to choose from
    @GET("data/2.5/weather?")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String = API_KEY,
        @Query("units") units: String = "imperial"
    ): Response<WeatherResponseModel>


}