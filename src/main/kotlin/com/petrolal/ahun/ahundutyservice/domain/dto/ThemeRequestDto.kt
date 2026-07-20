package com.petrolal.ahun.ahundutyservice.domain.dto

/**
 * Data Transfer Object (DTO) for creating or updating a [com.petrolal.ahun.ahundutyservice.domain.Theme].
 *
 * @property name The name of the theme.
 * @property description The descriptive text for the theme.
 */
data class ThemeRequestDto (
    val name: String,
    val description: String,
)