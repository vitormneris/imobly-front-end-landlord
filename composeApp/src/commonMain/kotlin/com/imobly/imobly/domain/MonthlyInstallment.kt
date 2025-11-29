package com.imobly.imobly.domain

import com.imobly.imobly.domain.enums.PaymentStatusEnum
import kotlinx.serialization.Serializable

@Serializable
data class MonthlyInstallment(
    val id: String? = null,
    val monthlyRent: String = "",
    val status: PaymentStatusEnum = PaymentStatusEnum.PENDING,
    val dueDate: String = "",
    val month: String = ""
)