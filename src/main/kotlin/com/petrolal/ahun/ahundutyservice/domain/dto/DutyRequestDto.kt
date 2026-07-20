package com.petrolal.ahun.ahundutyservice.domain.dto

import com.petrolal.ahun.ahundutyservice.domain.DutyTypeEnum
import com.petrolal.ahun.ahundutyservice.domain.SemesterEnum
import java.time.LocalDate
import java.util.UUID

data class DutyRequestDto(
    val themeId: UUID,
    val dutyType: DutyTypeEnum,
    val date: LocalDate,
    val period: SemesterEnum,
    val description: String? = null,
    val year: Int,
    val eventIds: List<UUID> = emptyList()
)
