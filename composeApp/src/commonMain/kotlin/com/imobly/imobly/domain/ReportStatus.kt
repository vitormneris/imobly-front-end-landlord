package com.imobly.imobly.domain

import kotlinx.serialization.Serializable

enum class ReportStatus(val description: String) {
    RESOLVED("Resolvido"),
    PENDING("Pendente"),
    NEW("Novo"),
    CLOSED("Fechado");

    override fun toString(): String = description
}