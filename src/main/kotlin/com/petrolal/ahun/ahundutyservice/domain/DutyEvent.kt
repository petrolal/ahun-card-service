package com.petrolal.ahun.ahundutyservice.domain

import java.time.LocalTime
import java.util.*

data class DutyEvent(
    var id: UUID,
    var name: String,
    var startedAt: LocalTime,
    var description: String? = null,
    var visibleInCard: Boolean
)