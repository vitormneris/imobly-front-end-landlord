package com.imobly.imobly.domain

import kotlinx.serialization.Serializable

@Serializable
data class Appointment(
    var id: String = "",
    var guestName: String = "",
    var moment: String = "",
    var telephone: String = "",
    var property: Property = Property()
)