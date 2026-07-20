package com.petrolal.ahun.ahundutyservice.domain

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class Duty(
    var id: UUID,
    var theme: Theme,
    var dutyType: DutyTypeEnum,
    var date: LocalDate,
    var period: SemesterEnum,
    var description: String? = null,
    var year: Int,
    var events: MutableList<DutyEvent>,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime? = null,
)