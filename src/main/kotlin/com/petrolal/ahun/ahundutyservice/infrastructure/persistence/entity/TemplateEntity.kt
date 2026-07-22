package com.petrolal.ahun.ahundutyservice.infrastructure.persistence.entity

import com.petrolal.ahun.ahundutyservice.domain.Template
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "template")
class TemplateEntity(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    var name: String,

    @Column(name = "image_path", nullable = false)
    var imagePath: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id")
    var theme: ThemeEntity? = null,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    fun toDomain(): Template {
        return Template(
            id = id,
            name = name,
            imagePath = imagePath,
            theme = theme?.let { ThemeEntity.toDomain(it) },
            createdAt = createdAt
        )
    }

    companion object {
        fun toEntity(domain: Template): TemplateEntity {
            return TemplateEntity(
                id = domain.id,
                name = domain.name,
                imagePath = domain.imagePath,
                theme = domain.theme?.let { ThemeEntity.toEntity(it) },
                createdAt = domain.createdAt
            )
        }
    }
}
