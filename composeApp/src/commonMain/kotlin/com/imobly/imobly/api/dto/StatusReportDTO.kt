package com.imobly.imobly.api.dto

import com.imobly.imobly.domain.enums.StatusReportEnum
import kotlinx.serialization.Serializable

@Serializable
class StatusReportDTO(val status: StatusReportEnum)