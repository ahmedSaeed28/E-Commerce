package com.example.e.commerce.data.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("title")
    val title: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("picture")
    val picture: String = "",
    @SerializedName("price")
    val price: Double = 0.0,
    @SerializedName("rating")
    val rating: Int = 0
)