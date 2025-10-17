package com.imobly.imobly.domain

import kotlinx.serialization.Serializable

@Serializable
data class Tenant(
    val id: String? = null,
    val pathImage: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String ="",
    var telephones: MutableList<Telephone> = mutableListOf(),
    val rg: String = "",
    val cpf: String = "",
    val nationality: String = "",
    val maritalStatus: MaritalStatus = MaritalStatus.SINGLE,
    val birthDate: String = "",
    val address: Address = Address()
)
