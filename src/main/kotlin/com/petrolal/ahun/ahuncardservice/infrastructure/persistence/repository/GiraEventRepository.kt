package com.petrolal.ahun.ahuncardservice.infrastructure.persistence.repository

import com.petrolal.ahun.ahuncardservice.domain.GiraEvent
import com.petrolal.ahun.ahuncardservice.domain.dto.GiraEventRequestDto
import com.petrolal.ahun.ahuncardservice.infrastructure.persistence.entity.GiraEventEntity
import com.petrolal.ahun.ahuncardservice.infrastructure.ports.GiraEventRepositoryPort
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
class GiraEventRepository(
    private val repository: GiraEventRepositoryJpa
) : GiraEventRepositoryPort {
    override fun findAll(): List<GiraEvent> {
        return repository.findAll().map { GiraEventEntity.toDomain(it) }
    }

    override fun save(events: List<GiraEventRequestDto>): List<GiraEvent> {
        val saved = mutableListOf<GiraEventEntity>()

        events.forEach {
            saved.add(
                repository.save(
                    GiraEventEntity(
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

        return saved.map { GiraEventEntity.toDomain(it) }
    }
}