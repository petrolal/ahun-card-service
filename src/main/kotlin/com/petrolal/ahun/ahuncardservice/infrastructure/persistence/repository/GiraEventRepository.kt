package com.petrolal.ahun.ahuncardservice.infrastructure.persistence.repository

import com.petrolal.ahun.ahuncardservice.domain.models.GiraEvent
import com.petrolal.ahun.ahuncardservice.domain.ports.GiraEventRepositoryPort
import com.petrolal.ahun.ahuncardservice.infrastructure.persistence.entity.GiraEventEntity
import org.springframework.stereotype.Repository

@Repository
class GiraEventRepository(
    private val repository: GiraEventRepositoryJpa
) : GiraEventRepositoryPort {
    override fun findAll(): List<GiraEvent> {
        return repository.findAll().map { GiraEventEntity.toDomain(it) }
    }
}