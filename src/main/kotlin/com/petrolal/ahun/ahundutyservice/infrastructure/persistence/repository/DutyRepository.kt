package com.petrolal.ahun.ahundutyservice.infrastructure.persistence.repository

import com.petrolal.ahun.ahundutyservice.application.ports.DutyRepositoryPort
import com.petrolal.ahun.ahundutyservice.domain.Duty
import com.petrolal.ahun.ahundutyservice.infrastructure.persistence.entity.DutyEntity
import org.springframework.stereotype.Repository

@Repository
class DutyRepository(
    private val repository: DutyRepositoryJpa
) : DutyRepositoryPort {

    override fun findByThemeName(themeName: String): List<Duty> {
        if (themeName.isBlank()) {
            throw IllegalArgumentException("Theme name cannot be blank")
        }

        return repository.findByThemeName(themeName)
            .map(DutyEntity::toDomain)
    }

    override fun create(duty: Duty): Duty {
        val entity = DutyEntity.toEntity(duty)
        val saved = repository.save(entity)
        return DutyEntity.toDomain(saved)
    }

    override fun findAll(): List<Duty> {
        return repository.findAll()
            .map(DutyEntity::toDomain)
    }
}
