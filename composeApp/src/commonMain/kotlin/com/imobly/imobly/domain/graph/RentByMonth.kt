package com.imobly.imobly.domain.graph

import kotlinx.serialization.Serializable

@Serializable
data class RentByMonth(
    val x: List<String> = emptyList(),
    val y: List<Float> = emptyList()
)