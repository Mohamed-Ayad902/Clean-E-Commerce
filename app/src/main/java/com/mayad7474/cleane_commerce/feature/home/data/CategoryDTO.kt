package com.mayad7474.cleane_commerce.feature.home.data

import com.google.gson.annotations.SerializedName

data class CategoryDTO(
    @SerializedName("_id") val id: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("image") val image: String,
    @SerializedName("name") val name: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("updatedAt") val updatedAt: String
)