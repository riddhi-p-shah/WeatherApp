package com.riddhi.weatherforecast.repository

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.riddhi.weatherforecast.datastore.DataStore
import com.riddhi.weatherforecast.utils.dataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * DataStoreRepository class for interacting with shared preferences using DataStore.
 */
class DataStoreRepository @Inject constructor(
    private val context: Context
) : DataStore {

    /**
     * Stores a string value for the given key in shared preferences.
     *
     * @param key The key to associate with the value.
     * @param value The string value to store.
     */
    override suspend fun putString(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    /**
     * Stores a double value for the given key in shared preferences.
     *
     * @param key The key to associate with the value.
     * @param value The double value to store.
     */
    override suspend fun putDouble(key: String, value: Double) {
        val preferencesKey = doublePreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    /**
     * Retrieves a string value mapped with given key from shared preferences.
     *
     * @param key The key to search for.
     * @return The retrieved string value, or null if the key is not found or an error occurs.
     */
    override suspend fun getString(key: String): String? {
        return try {
            val preferencesKey = stringPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            preferences[preferencesKey]
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    /**
     * Retrieves a double value mapped with given key from shared preferences.
     *
     * @param key The key to search for.
     * @return The retrieved double value, or null if the key is not found or an error occurs.
     */
    override suspend fun getDouble(key: String): Double? {
        return try {
            val preferencesKey = doublePreferencesKey(key)
            val preferences = context.dataStore.data.first()
            preferences[preferencesKey]
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }
}