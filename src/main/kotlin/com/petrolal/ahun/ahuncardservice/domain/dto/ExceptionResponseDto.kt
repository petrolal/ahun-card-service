package com.petrolal.ahun.ahuncardservice.domain.dto

import java.time.LocalDateTime

data class ExceptionResponseDto(
    val status: Int,
    val error: String,
    val message: String?,
    val timestamp: LocalDateTime = LocalDateTime.now(),
)