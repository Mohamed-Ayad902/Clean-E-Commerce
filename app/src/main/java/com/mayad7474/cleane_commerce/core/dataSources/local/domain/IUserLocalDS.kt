package com.mayad7474.cleane_commerce.core.dataSources.local.domain

interface IUserLocalDS {
    suspend fun clearDataStore(): Boolean
    suspend fun saveUserCredentials(userCredentials: String)
    suspend fun getUserCredentials(): String
    suspend fun saveUserToken(userId: String)
    suspend fun getUserToken(): String
}