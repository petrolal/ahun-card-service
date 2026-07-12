package com.petrolal.ahun.ahuncardservice.infrastructure.persistence.repository

import com.petrolal.ahun.ahuncardservice.domain.models.Theme
import com.petrolal.ahun.ahuncardservice.domain.ports.ThemeRepositoryPort
import com.petrolal.ahun.ahuncardservice.infrastructure.persistence.entity.ThemeEntity
import org.springframework.stereotype.Repository

@Repository
class ThemeRepository (
    private val repository: ThemeRepositoryJpa
) : ThemeRepositoryPort {
    override fun findAll(): List<Theme> {
        return repository.findAll()
            .map { Theme(it.id, it.name, it.description) }
    }

    override fun findByName(themeName: String): List<Theme> {
        if (themeName.isBlank()) {
            throw IllegalArgumentException("Theme name cannot be blank")
        }

        return repository.findByName(themeName)
            .map { Theme(it.id, it.name, it.description) }
    }

    override fun create(theme: Theme): Theme {
        val entity = repository.save(ThemeEntity.toEntity(theme))
        return ThemeEntity.toDomain(entity)
    }

}