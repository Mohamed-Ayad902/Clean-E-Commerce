package com.mayad7474.cleane_commerce.feature.home.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val createdAt: String,
    val image: String,
    val name: String,
    val slug: String,
    val updatedAt: String
)