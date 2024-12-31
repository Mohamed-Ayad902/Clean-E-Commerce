package com.mayad7474.cleane_commerce.core.dataSources.local.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mayad7474.cleane_commerce.core.security.SecurityUtil
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserDataStore @Inject constructor(
    dataStore: DataStore<Preferences>, security: SecurityUtil, securityKeyAlias: String
) : DataStoreUtil(dataStore, security, securityKeyAlias) {

    internal suspend fun saveUserCredentials(userCredentials: String) =
        setSecuredDataAsString(USER_CREDENTIALS_KEY, userCredentials)

    internal suspend fun getUserCredentials(): String =
        getSecuredDataAsString(USER_CREDENTIALS_KEY).first()

    internal suspend fun saveUserToken(userId: String) =
        setSecuredDataAsString(USER_TOKEN, userId)

    internal suspend fun getUserToken(): String =
        getSecuredDataAsString(USER_TOKEN).first()

    companion object {
        private val USER_CREDENTIALS_KEY = stringPreferencesKey("user_credentials")
        private val USER_TOKEN = stringPreferencesKey("USER_TOKEN")
    }
}