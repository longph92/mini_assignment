package com.example.dd_mini_assignment.domain.model

import com.google.gson.annotations.SerializedName

data class IngredientModel(
    @SerializedName("id") val id: String,
    @SerializedName("category_id") val categoryId: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("title") val title: String,
    @SerializedName("quantity") val quantity: Int,
)