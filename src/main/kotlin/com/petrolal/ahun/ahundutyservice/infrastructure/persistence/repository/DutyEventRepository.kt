package com.petrolal.ahun.ahundutyservice.infrastructure.persistence.repository

import com.petrolal.ahun.ahundutyservice.domain.DutyEvent
import com.petrolal.ahun.ahundutyservice.domain.dto.DutyEventRequestDto
import com.petrolal.ahun.ahundutyservice.infrastructure.persistence.entity.DutyEventEntity
import com.petrolal.ahun.ahundutyservice.infrastructure.ports.DutyEventRepositoryPort
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
class DutyEventRepository(
    private val repository: DutyEventRepositoryJpa
) : DutyEventRepositoryPort {
    override fun findAll(): List<DutyEvent> {
        return repository.findAll().map { DutyEventEntity.toDomain(it) }
    }

    override fun save(events: List<DutyEventRequestDto>): List<DutyEvent> {
        val saved = mutableListOf<DutyEventEntity>()

        events.forEach {
            saved.add(
                repository.save(
                    DutyEventEntity(
                        UUID.randomUUID(),
                        it.name,
                        it.startedAt,
                        it.visibleInCard,
                        it.description,
                        LocalDateTime.now(),
                        null,
                    )
                )
            )
        }

        return saved.map { DutyEventEntity.toDomain(it) }
    }
}