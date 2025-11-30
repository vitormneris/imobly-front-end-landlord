package com.imobly.imobly.domain

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Appointment(
    var id: String = "",
    var guestName: String = "",
    var moment: LocalDateTime = LocalDateTime.parse("2025-01-01T00:00:00.00"),
    var guideName: String = "",
    var telephone: String = "",
    var property: Property = Property()
)