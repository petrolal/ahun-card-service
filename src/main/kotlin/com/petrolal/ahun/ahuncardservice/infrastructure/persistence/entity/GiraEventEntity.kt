package com.petrolal.ahun.ahuncardservice.infrastructure.persistence.entity

import com.petrolal.ahun.ahuncardservice.domain.GiraEvent
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.sql.Time
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "gira_events")
class GiraEventEntity(
    @Id
    var id: UUID,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var startedAt: Time,

    @Column(nullable = false)
    var visibleInCard: Boolean,

    @Column(nullable = true)
    var description: String?,

    @Column(nullable = false)
    var createdAt: LocalDateTime,

    @Column(nullable = true)
    var updatedAt: LocalDateTime?,
) {
    companion object {
        fun toDomain(giraEventEntity: GiraEventEntity): GiraEvent {
            return GiraEvent(
                id = giraEventEntity.id,
                name = giraEventEntity.name,
                startedAt = giraEventEntity.startedAt,
                visibleInCard = giraEventEntity.visibleInCard,
                description = giraEventEntity.description,
            )
        }
    }
}