package com.imobly.imobly.domain

enum class MaritalStatus(val label: String) {
    MARRIED("Casado"),
    SINGLE("Solteiro"),
    WIDOWED("Viúvo"),
    DIVORCED("Divorciado");

    override fun toString(): String = label

}