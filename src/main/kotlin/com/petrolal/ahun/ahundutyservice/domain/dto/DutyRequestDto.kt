package com.petrolal.ahun.ahundutyservice.domain.dto

import com.petrolal.ahun.ahundutyservice.domain.DutyTypeEnum
import java.time.LocalDate
import java.util.*

/**
 * Data Transfer Object (DTO) for creating a new [com.petrolal.ahun.ahundutyservice.domain.Duty].
 *
 * @property themeId Unique identifier of the associated [com.petrolal.ahun.ahundutyservice.domain.Theme].
 * @property dutyType The type/category of the duty.
 * @property date The date when the duty is scheduled.
 * @property period The academic semester/period of the duty.
 * @property description Optional descriptive text about the duty.
 * @property year The year of the duty.
 * @property eventIds List of unique identifiers of associated [com.petrolal.ahun.ahundutyservice.domain.DutyEvent]s.
 */
data class DutyRequestDto(
    val themeId: UUID,
    val dutyType: DutyTypeEnum,
    val date: LocalDate,
    val description: String? = null,
    val year: Int,
    val eventIds: List<UUID> = emptyList()
)
