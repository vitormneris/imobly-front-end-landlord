package com.imobly.imobly.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class LeaseDTO(
    val startDate: String,
    val endDate: String,
    val propertyId: String,
    val tenantId: String,
    val monthlyRent: String,
    val securityDeposit: String?,
    val paymentDueDay: String
)