package com.imobly.imobly.domain.enums

enum class MaritalStatusEnum(val description: String) {
    MARRIED("Casado"),
    SINGLE("Solteiro"),
    WIDOWED("Viúvo"),
    DIVORCED("Divorciado");

    override fun toString(): String = description

}