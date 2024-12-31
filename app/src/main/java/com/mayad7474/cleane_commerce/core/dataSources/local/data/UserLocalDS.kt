package com.mayad7474.cleane_commerce.core.dataSources.local.data

import com.mayad7474.cleane_commerce.core.dataSources.local.domain.IUserLocalDS
import javax.inject.Inject

class UserLocalDS @Inject constructor(private val localDS: UserDataStore) : IUserLocalDS {

    override suspend fun clearDataStore() = localDS.clearDataStore()

    override suspend fun saveUserCredentials(userCredentials: String) =
        localDS.saveUserCredentials(userCredentials)

    override suspend fun getUserCredentials(): String = localDS.getUserCredentials()

    override suspend fun saveUserToken(userId: String) = localDS.saveUserToken(userId)

    override suspend fun getUserToken() = localDS.getUserToken()
}