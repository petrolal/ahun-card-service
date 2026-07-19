package com.petrolal.ahun.ahundutyservice.infrastructure.persistence.entity

import com.petrolal.ahun.ahundutyservice.domain.DutyEvent
import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

@Entity
@Table(name = "duty_events")
class DutyEventEntity(
    @Id
    var id: UUID,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var startedAt: LocalTime,

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
        fun toDomain(dutyEventEntity: DutyEventEntity): DutyEvent {
            return DutyEvent(
                id = dutyEventEntity.id,
                name = dutyEventEntity.name,
                startedAt = dutyEventEntity.startedAt,
                visibleInCard = dutyEventEntity.visibleInCard,
                description = dutyEventEntity.description
            )
        }
    }
}