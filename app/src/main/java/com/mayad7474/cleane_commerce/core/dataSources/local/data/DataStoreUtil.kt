package com.mayad7474.cleane_commerce.core.dataSources.local.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.google.gson.Gson
import com.mayad7474.cleane_commerce.core.security.SecurityUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.IOException

abstract class DataStoreUtil(
    private val dataStore: DataStore<Preferences>, private val security: SecurityUtil,
    private val securityKeyAlias: String
) {

    protected fun <T> getData(key: Preferences.Key<T>): Flow<T?> =
        dataStore.data.catch { handleEmptyError(it) }.map { it[key] }

    protected suspend fun <T> setData(key: Preferences.Key<T>, value: T): Boolean {
        return dataStore.edit { it[key] = value }.contains(key)
    }

    // ----------------------------------------- Boolean ------------------------------------------

    protected fun getBooleanData(key: Preferences.Key<Boolean>): Flow<Boolean> =
        dataStore.data.catch { handleBooleanError(it) }.map { it[key] ?: false }

    protected suspend fun setBooleanData(key: Preferences.Key<Boolean>, value: Boolean): Boolean {
        return dataStore.edit { it[key] = value }.contains(key)
    }

    // ----------------------------------------- String --------------------------------------------

    protected fun getSecuredDataAsString(key: Preferences.Key<String>): Flow<String> =
        dataStore.data.catch { handleEmptyError(it) }.secureMap { it[key].orEmpty() }

    protected suspend fun setSecuredDataAsString(key: Preferences.Key<String>, value: String) {
        dataStore.secureEdit(value) { prefs, encryptedValue -> prefs[key] = encryptedValue }
    }

    protected suspend fun clearSecuredData(key: Preferences.Key<String>): Boolean {
        return dataStore.edit { it[key] = "" }[key]?.isEmpty() == true
    }

    // ----------------------------------------- Public --------------------------------------------

    suspend fun hasKey(key: Preferences.Key<*>): Boolean {
        var hasKey = false
        dataStore.edit { hasKey = it.contains(key) }
        return hasKey
    }

    open suspend fun clearDataStore() = dataStore.edit {
        it.clear()
    }.asMap().isEmpty()


    // ----------------------------------------- Settings ------------------------------------------

    /**
     * serializes data type into string
     * performs encryption
     * stores encrypted data in DataStore
     */
    private suspend inline fun <reified T> DataStore<Preferences>.secureEdit(
        value: T, crossinline editStore: (MutablePreferences, String) -> Unit
    ) = edit {
        val encryptedValue = security.encryptData(securityKeyAlias, Gson().toJson(value))
        editStore.invoke(it, encryptedValue)
    }

    /**
     * fetches encrypted data from DataStore
     * performs decryption
     * deserializes data into respective data type
     */
    private inline fun <reified T> Flow<Preferences>.secureMap(crossinline fetchValue: (value: Preferences) -> String): Flow<T> {
        return map { prefs ->
            if (fetchValue(prefs).isEmpty())
                "" as T
            else {
                val decryptedValue = security.decryptData(securityKeyAlias, fetchValue(prefs))
                Gson().fromJson(decryptedValue, T::class.java)
            }
        }
    }

    // --------------------------------------------- PrefsErrors -----------------------------------

    private fun handleEmptyError(it: Throwable) = flow {
        if (it is IOException) {
            it.printStackTrace()
            emit(emptyPreferences())
        } else
            throw it
    }

    private fun handleBooleanError(it: Throwable) = flow {
        if (it is IOException) {
            it.printStackTrace()
            emit(false)
        } else
            throw it
    }
}