package com.mayad7474.cleane_commerce.core.dataSources.local.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mayad7474.cleane_commerce.feature.home.data.CategoryEntity

@Database(
    entities = [CategoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ECommerceDB: RoomDatabase() {

}