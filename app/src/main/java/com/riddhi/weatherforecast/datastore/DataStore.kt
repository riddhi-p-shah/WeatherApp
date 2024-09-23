package com.riddhi.weatherforecast.datastore

interface DataStore {
    suspend fun putString(key: String, value: String)
    suspend fun putDouble(key: String, value: Double)
    suspend fun getString(key: String): String?
    suspend fun getDouble(key: String): Double?
}