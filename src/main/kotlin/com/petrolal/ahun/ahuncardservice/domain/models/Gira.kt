package com.petrolal.ahun.ahuncardservice.domain.models

import java.time.LocalDate
import java.util.UUID

data class Gira(
    var id: UUID,
    var theme: Theme,
    var date: LocalDate,
    var period: SemesterEnum,
    var description: String? = null,
    var year: Int,
    var events: MutableList<GiraEvent>,
)