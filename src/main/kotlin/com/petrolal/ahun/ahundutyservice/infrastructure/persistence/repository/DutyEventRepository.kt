package com.petrolal.ahun.ahundutyservice.infrastructure.persistence.repository

import com.petrolal.ahun.ahundutyservice.application.ports.DutyEventRepositoryPort
import com.petrolal.ahun.ahundutyservice.domain.DutyEvent
import com.petrolal.ahun.ahundutyservice.infrastructure.persistence.entity.DutyEventEntity
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class DutyEventRepository(
    private val repository: DutyEventRepositoryJpa
) : DutyEventRepositoryPort {
    override fun findAll(): List<DutyEvent> {
        return repository.findAll()
            .map(DutyEventEntity::toDomain)
    }

    override fun create(events: List<DutyEvent>): List<DutyEvent> {
        return events.map { event ->
            val entity = DutyEventEntity.toEntity(event)
            DutyEventEntity.toDomain(repository.save(entity))
        }
    }

    override fun findAllById(ids: List<UUID>): List<DutyEvent> {
        return repository.findAllById(ids)
            .map(DutyEventEntity::toDomain)
    }
}