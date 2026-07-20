package com.petrolal.ahun.ahundutyservice.domain

import java.time.LocalDateTime
import java.util.UUID

/**
 * Domain model representing a Theme.
 * A Theme acts as a category or context for duties and cards.
 *
 * @property id Unique identifier of the theme.
 * @property name The name of the theme.
 * @property description Optional descriptive text about the theme.
 * @property createdAt Timestamp when the theme was created.
 * @property updatedAt Optional timestamp when the theme was last updated.
 */
data class Theme (
    var id: UUID,
    var name: String,
    var description: String? = null,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime? = null,
)