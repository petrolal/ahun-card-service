package com.petrolal.ahun.ahundutyservice.domain.dto

import java.time.LocalTime

data class DutyEventRequestDto(
    var name: String,
    var startedAt: LocalTime,
    var description: String? = null,
    var visibleInCard: Boolean,
)
