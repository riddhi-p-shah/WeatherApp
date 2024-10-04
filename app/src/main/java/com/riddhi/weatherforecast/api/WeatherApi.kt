package com.riddhi.weatherforecast.api

import com.riddhi.weatherforecast.models.GeoCodeModel
import com.riddhi.weatherforecast.models.WeatherResponseModel
import com.riddhi.weatherforecast.utils.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit interface defining the API endpoints
 */
interface WeatherApi {

    /**
     * Fetches geocode information based on a given query.
     *
     * @param query The search query for the location.
     * @param limit The maximum number of results to return (default: 10).
     * @param appid The API key for accessing the service.
     * @return A response containing a list of GeoCodeModel objects.
     */
    @GET("geo/1.0/direct")
    suspend fun getGeoCode(
        @Query("q") query: String,
        @Query("limit") limit: Int = 10,
        @Query("appid") appid: String = API_KEY
    ): Response<List<GeoCodeModel>>

    /**
     * Fetches weather information based on latitude and longitude coordinates.
     *
     * @param lat The latitude coordinate.
     * @param lon The longitude coordinate.
     * @param appid The API key for accessing the service.
     * @param units The units of measurement for temperature and wind speed (default: "imperial").
     * @return A response containing a WeatherResponseModel object.
     */

    //if given more time.. I would use enums for units and allow an option to users to choose from
    @GET("data/2.5/weather?")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String = API_KEY,
        @Query("units") units: String = "imperial"
    ): Response<WeatherResponseModel>


}