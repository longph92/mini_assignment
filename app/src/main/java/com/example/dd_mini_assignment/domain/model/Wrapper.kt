package com.example.dd_mini_assignment.domain.model

data class Wrapper<out T>(
    val data: T?,
    val status: Status
)

data class Status(
    val success: Boolean,
    val statusCode: Int,
    val message: String
)