package com.example.dd_mini_assignment.domain.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class OrderModel(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("addon") val addons: List<AddonModel>,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("created_at") var createdAt: Date,
    @SerializedName("alerted_at") var alertedAt: Date,
    @SerializedName("expired_at") var expiredAt: Date
) {
    var foods: List<FoodModel> = listOf()

    var createdAtInMilliseconds = createdAt.time
    var alertedAtInMilliseconds = alertedAt.time
    var expiredAtInMilliseconds = expiredAt.time

    var currentState: OrderState = OrderState.IN_COMING
    var isPlayRingTone = false

    fun updateAllTimes() {
        createdAtInMilliseconds = createdAt.time
        alertedAtInMilliseconds = alertedAt.time
        expiredAtInMilliseconds = expiredAt.time
    }
}

