package com.petrolal.ahun.ahundutyservice.infrastructure.persistence.repository

import com.petrolal.ahun.ahundutyservice.application.ports.ThemeRepositoryPort
import com.petrolal.ahun.ahundutyservice.domain.Theme
import com.petrolal.ahun.ahundutyservice.domain.exception.ResourceNotFoundException
import com.petrolal.ahun.ahundutyservice.infrastructure.persistence.entity.ThemeEntity
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Persistence database adapter implementing [ThemeRepositoryPort] for database interaction.
 * Handles data mapping between [Theme] domain models and [ThemeEntity] database entities.
 */
@Repository
class ThemeRepository(
    private val repository: ThemeRepositoryJpa
) : ThemeRepositoryPort {

    /**
     * Fetches all themes and maps them to domain models.
     */
    override fun findAll(): List<Theme> {
        return repository.findAll()
            .map(ThemeEntity::toDomain)
    }

    /**
     * Filters themes by their name.
     */
    override fun filterByName(name: String): List<Theme> {
        return repository.filterByName(name)
            .map(ThemeEntity::toDomain)
    }

    /**
     * Saves a new theme to the database.
     */
    override fun create(theme: Theme): Theme {
        val entity = ThemeEntity.toEntity(theme)
        return ThemeEntity.toDomain(repository.save(entity))
    }

    /**
     * Updates an existing theme category in the database.
     */
    override fun update(id: UUID, theme: Theme): Theme {
        val entityOptional = repository.findById(id)

        if (entityOptional.isEmpty) {
            throw ResourceNotFoundException("Theme with id $id does not exist")
        }

        val entity = entityOptional.get()
        entity.name = theme.name
        entity.description = theme.description
        entity.updatedAt = theme.updatedAt

        return ThemeEntity.toDomain(repository.save(entity))
    }

    /**
     * Searches for a theme in the database by its unique identifier.
     */
    override fun findById(id: UUID): Theme? {
        val entityOptional = repository.findById(id)
        if (entityOptional.isEmpty) return null
        return ThemeEntity.toDomain(entityOptional.get())
    }
}