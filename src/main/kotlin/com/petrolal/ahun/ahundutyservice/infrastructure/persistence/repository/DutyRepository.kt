package com.petrolal.ahun.ahundutyservice.infrastructure.persistence.repository

import com.petrolal.ahun.ahundutyservice.application.ports.DutyRepositoryPort
import com.petrolal.ahun.ahundutyservice.domain.Duty
import com.petrolal.ahun.ahundutyservice.infrastructure.persistence.entity.DutyEntity
import org.springframework.stereotype.Repository

/**
 * Persistence database adapter implementing [DutyRepositoryPort] for database interaction.
 * Handles data mapping between [Duty] domain models and [DutyEntity] database entities.
 */
@Repository
class DutyRepository(
    private val repository: DutyRepositoryJpa
) : DutyRepositoryPort {

    /**
     * Finds all duties associated with a specific theme name.
     */
    override fun findByThemeName(themeName: String): List<Duty> {
        if (themeName.isBlank()) {
            throw IllegalArgumentException("Theme name cannot be blank")
        }

        return repository.findByThemeName(themeName)
            .map(DutyEntity::toDomain)
    }

    /**
     * Saves a new duty assignment to the database.
     */
    override fun create(duty: Duty): Duty {
        val entity = DutyEntity.toEntity(duty)
        val saved = repository.save(entity)
        return DutyEntity.toDomain(saved)
    }

    /**
     * Fetches all duties and maps them to domain models.
     */
    override fun findAll(): List<Duty> {
        return repository.findAll()
            .map(DutyEntity::toDomain)
    }
}
