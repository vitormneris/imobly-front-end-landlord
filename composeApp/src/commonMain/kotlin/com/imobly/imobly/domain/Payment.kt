package com.imobly.imobly.domain

import kotlinx.serialization.Serializable

@Serializable
data class Payment(
    val issueDate: String = "",
    val dueDate: String = "",
    val rentalValue: Double = 0.0,
    val moment: String = "",
    val qrcode: String = "",
    val status: String = ""
)