package com.petrolal.ahun.ahundutyservice.domain

import java.util.UUID

data class Theme (
    var id: UUID,
    var name: String,
    var description: String? = null,
)