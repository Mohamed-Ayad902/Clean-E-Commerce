package com.mayad7474.cleane_commerce.core.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.mayad7474.cleane_commerce.core.dataSources.local.data.ECommerceDB
import com.mayad7474.cleane_commerce.core.dataSources.local.data.UserDataStore
import com.mayad7474.cleane_commerce.core.dataSources.local.data.UserLocalDS
import com.mayad7474.cleane_commerce.core.dataSources.local.domain.IUserLocalDS
import com.mayad7474.cleane_commerce.core.security.SecurityUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.IOException
import java.util.Properties
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDSModule {

    @Singleton
    @Provides
    fun provideUserLocalDS(userDataStore: UserDataStore): IUserLocalDS = UserLocalDS(userDataStore)

    @Singleton
    @Provides
    fun provideUserDataStore(
        @ApplicationContext context: Context, security: SecurityUtil, securityProps: Properties,
    ): UserDataStore {
        val dataStore = preferencesDataStore("user-dataStore").getValue(context, String::javaClass)
        return UserDataStore(dataStore, security, securityProps.getProperty("KeyAlias"))
    }

    @Provides
    fun provideSecurityProperties(@ApplicationContext context: Context): Properties {
        val properties = Properties()
        try {
            val inputStream = context.assets.open("security.properties")
            properties.load(inputStream)
        } catch (e: IOException) {
            throw IOException("security.properties file not found!!!")
        }
        return properties
    }

    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ): ECommerceDB =
        Room.databaseBuilder(context, ECommerceDB::class.java, "e_commerce_room_db").build()
}