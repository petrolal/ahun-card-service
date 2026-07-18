package com.petrolal.ahun.ahuncardservice.domain

import java.sql.Time
import java.util.*

data class GiraEvent (
    var id: UUID,
    var name: String,
    var startedAt: Time,
    var description: String? = null,
    var visibleInCard: Boolean
)