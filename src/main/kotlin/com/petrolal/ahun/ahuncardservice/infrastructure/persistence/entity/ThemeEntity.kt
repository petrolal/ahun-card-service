package com.petrolal.ahun.ahuncardservice.infrastructure.persistence.entity

import com.petrolal.ahun.ahuncardservice.domain.models.Theme
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "theme")
class ThemeEntity(
    @Id
    var id: UUID,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = true)
    var description: String?,

    @Column(nullable = false)
    var createdAt: LocalDateTime,

    @Column(nullable = true)
    var updatedAt: LocalDateTime?,

    ) {
    companion object {
        fun toEntity(theme: Theme): ThemeEntity {
            return ThemeEntity(
                id = theme.id,
                name = theme.name,
                description = theme.description,
                createdAt = LocalDateTime.now(),
                updatedAt = null,
            )
        }

        fun toDomain(themeEntity: ThemeEntity): Theme {
            return Theme(
                id = themeEntity.id,
                name = themeEntity.name,
                description = themeEntity.description,
            )
        }
    }
}