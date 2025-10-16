package com.imobly.imobly.domain

import kotlinx.serialization.Serializable

@Serializable
data class ResponseMessage(
    val code: String = "",
    val status: Int = 200,
    val message: String = "",
    val timestamp: String = "",
    val path: String = "",
    val errorFields: List<ErrorField>? = null
)


