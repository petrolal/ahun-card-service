package com.petrolal.ahun.ahuncardservice.domain.models

import java.util.UUID

data class Theme (
    var id: UUID,
    var name: String,
    var description: String? = null,
)