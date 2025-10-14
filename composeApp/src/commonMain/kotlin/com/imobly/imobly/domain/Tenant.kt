package com.imobly.imobly.domain

import org.jetbrains.compose.resources.DrawableResource

data class Tenant(
    val id: String,
    val name: String,
    val property: String,
    val phone: String,
    val email: String,
    val imageResource: DrawableResource
)