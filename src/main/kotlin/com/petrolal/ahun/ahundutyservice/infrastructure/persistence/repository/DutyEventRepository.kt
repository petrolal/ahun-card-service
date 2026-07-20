package com.petrolal.ahun.ahundutyservice.infrastructure.persistence.repository

import com.petrolal.ahun.ahundutyservice.infrastructure.persistence.entity.DutyEventEntity
import com.petrolal.ahun.ahundutyservice.infrastructure.ports.DutyEventRepositoryPort
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
class DutyEventRepository(
    private val repository: DutyEventRepositoryJpa
) : DutyEventRepositoryPort {
    override fun findAll(): List<DutyEventEntity> {
        return repository.findAll()
    }

    override fun create(events: List<DutyEventEntity>): List<DutyEventEntity> {
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

        return saved
    }
}