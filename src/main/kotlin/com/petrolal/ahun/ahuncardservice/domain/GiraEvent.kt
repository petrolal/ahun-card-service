package com.petrolal.ahun.ahuncardservice.domain

import java.time.LocalTime
import java.util.*

data class GiraEvent(
    var id: UUID,
    var name: String,
    var startedAt: LocalTime,
    var description: String? = null,
    var visibleInCard: Boolean
)