package com.petrolal.ahun.ahuncardservice.domain.dto

import java.time.LocalTime

data class GiraEventRequestDto(
    var name: String,
    var startedAt: LocalTime,
    var description: String? = null,
    var visibleInCard: Boolean,
)
