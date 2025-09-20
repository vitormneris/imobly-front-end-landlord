package com.imobly.imobly.domain.model

data class Property(
    val title: String = "",
    val description: String = "",
    val rentValue: String = "",
    val available: Boolean = false,
    var address: Address = Address(),
    val bedrooms: String = "",
    val bathrooms: String = "",
    val area: String = "",
    val garageSpaces: String = ""

) {

}