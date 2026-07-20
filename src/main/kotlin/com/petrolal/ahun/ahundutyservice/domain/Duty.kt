package com.petrolal.ahun.ahundutyservice.domain

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

/**
 * Domain model representing a Duty assignment.
 * A Duty groups a specific [Theme] and a set of [DutyEvent]s scheduled on a particular [date].
 *
 * @property id Unique identifier of the duty.
 * @property theme The [Theme] associated with this duty.
 * @property dutyType The type/category of the duty.
 * @property date The date when the duty is scheduled.
 * @property period The academic semester/period of the duty.
 * @property description Optional descriptive text about the duty.
 * @property year The year of the duty.
 * @property events The set of [DutyEvent]s associated with this duty.
 * @property createdAt Timestamp when the duty was created.
 * @property updatedAt Optional timestamp when the duty was last updated.
 */
data class Duty(
    var id: UUID,
    var theme: Theme,
    var dutyType: DutyTypeEnum,
    var date: LocalDate,
    var period: SemesterEnum,
    var description: String? = null,
    var year: Int,
    var events: MutableSet<DutyEvent>,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime? = null,
)