package com.imobly.imobly.domain.graph

import kotlinx.serialization.Serializable

@Serializable
data class RentedProperties(
    val x: List<String> = emptyList(),
    val y: List<Float> = emptyList()
)