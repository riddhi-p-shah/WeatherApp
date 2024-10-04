package com.riddhi.weatherforecast.datastore

/**
 * Interface defining the methods for storing and retrieving values in shared preferences.
 */
interface DataStore {

    /**
     * Stores a string value for a given key in shared preferences.
     *
     * @param key The key to associate with the value.
     * @param value The string data/value to store.
     */
    suspend fun putString(key: String, value: String)

    /**
     * Stores a double value under a given key in shared preferences.
     *
     * @param key The key to associate with the value.
     * @param value The double data/value to store.
     */
    suspend fun putDouble(key: String, value: Double)

    /**
     * Retrieves a string value based on a given key from shared preferences.
     *
     * @param key The key to search for.
     * @return The retrieved string value, or null if not found.
     */
    suspend fun getString(key: String): String?

    /**
     * Retrieves a double value based on a given key from shared preferences.
     *
     * @param key The key to search for.
     * @return The retrieved double value, or null if not found.
     */
    suspend fun getDouble(key: String): Double?
}