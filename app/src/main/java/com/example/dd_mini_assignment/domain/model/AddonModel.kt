package com.example.dd_mini_assignment.domain.model

import com.google.gson.annotations.SerializedName

data class AddonModel(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("quantity") val quantity: Int
)