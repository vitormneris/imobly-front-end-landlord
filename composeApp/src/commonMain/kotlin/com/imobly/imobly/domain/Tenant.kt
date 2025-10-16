package com.imobly.imobly.domain

import imobly.composeapp.generated.resources.Res
import imobly.composeapp.generated.resources.image_logo
import org.jetbrains.compose.resources.DrawableResource

data class Tenant(
    val id: String? = null,
    val name: String = "",
    val property: String = "",
    val email: String = "",
    val imageResource: DrawableResource = Res.drawable.image_logo,
    val telephones: Array<String> = Array(2) {""},
    val rg: String = "",
    var cpf: String = "",
    val nationality: String = "",
    val maritalStatus: MaritalStatus = MaritalStatus.SINGLE,
    val birthDate: String ="",
    val job: String = ""
)