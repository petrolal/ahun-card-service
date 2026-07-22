package com.petrolal.ahun.ahundutyservice.infrastructure.persistence.repository

import com.petrolal.ahun.ahundutyservice.application.ports.DutyRepositoryPort
import com.petrolal.ahun.ahundutyservice.domain.Duty
import com.petrolal.ahun.ahundutyservice.domain.DutyTypeEnum
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
     * Finds all duties associated with a specific duty type.
     */
    override fun findByDutyType(dutyType: DutyTypeEnum): List<Duty> {
        return repository.findByDutyType(dutyType)
            .map(DutyEntity::toDomain)
    }

    /**
     * Finds all duties associated with a theme name and duty type.
     */
    override fun findByThemeNameAndDutyType(themeName: String, dutyType: DutyTypeEnum): List<Duty> {
        if (themeName.isBlank()) {
            throw IllegalArgumentException("Theme name cannot be blank")
        }

        return repository.findByThemeNameAndDutyType(themeName, dutyType)
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

    /**
     * Finds a duty by its ID.
     */
    override fun findById(id: java.util.UUID): Duty? {
        return repository.findByIdCustom(id)
            .map(DutyEntity::toDomain)
            .orElse(null)
    }

    /**
     * Finds the duty matching the specified type in the given month and year, falling back to the latest duty of that type if none found for the exact month.
     */
    override fun findCurrentMonthDutyByType(dutyType: DutyTypeEnum, year: Int, month: Int): Duty? {
        val currentMonthDuties = repository.findByDutyTypeAndMonthAndYear(dutyType, month, year)
        if (currentMonthDuties.isNotEmpty()) {
            return DutyEntity.toDomain(currentMonthDuties.first())
        }
        val latestDuties = repository.findLatestByDutyType(dutyType)
        return latestDuties.firstOrNull()?.let(DutyEntity::toDomain)
    }

    /**
     * Updates an existing duty assignment in the database.
     */
    override fun update(id: java.util.UUID, duty: Duty): Duty {
        val existingEntity = repository.findByIdCustom(id)
            .orElseThrow { com.petrolal.ahun.ahundutyservice.domain.exception.ResourceNotFoundException("Duty with id $id not found") }

        existingEntity.date = duty.date
        existingEntity.theme = com.petrolal.ahun.ahundutyservice.infrastructure.persistence.entity.ThemeEntity.toEntity(duty.theme)
        existingEntity.dutyType = duty.dutyType
        existingEntity.period = duty.period
        existingEntity.description = duty.description
        existingEntity.year = duty.year
        existingEntity.events = duty.events.map { com.petrolal.ahun.ahundutyservice.infrastructure.persistence.entity.DutyEventEntity.toEntity(it) }.toMutableSet()
        existingEntity.updatedAt = java.time.LocalDateTime.now()

        val saved = repository.save(existingEntity)
        return DutyEntity.toDomain(saved)
    }
}
