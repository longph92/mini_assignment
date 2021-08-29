package com.example.dd_mini_assignment.domain.model

data class FoodModel(
    val id: String,
    val title: String,
    val quantity: Int,
    val addons: List<AddonModel>
)