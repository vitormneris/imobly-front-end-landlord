package com.imobly.imobly.domain

import kotlinx.serialization.Serializable

@Serializable
data class LandLord(
    val id: String? = null,
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val telephones: Telephone = Telephone()
)