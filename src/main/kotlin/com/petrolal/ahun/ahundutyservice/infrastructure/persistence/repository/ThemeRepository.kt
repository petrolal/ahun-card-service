package com.petrolal.ahun.ahundutyservice.infrastructure.persistence.repository

import com.petrolal.ahun.ahundutyservice.domain.Theme
import com.petrolal.ahun.ahundutyservice.domain.dto.ThemeRequestDto
import com.petrolal.ahun.ahundutyservice.domain.exception.ResourceNotFoundException
import com.petrolal.ahun.ahundutyservice.infrastructure.persistence.entity.ThemeEntity
import com.petrolal.ahun.ahundutyservice.infrastructure.ports.ThemeRepositoryPort
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
class ThemeRepository(
    private val repository: ThemeRepositoryJpa
) : ThemeRepositoryPort {
    override fun findAll(): List<Theme> {
        return repository.findAll()
            .map { Theme(it.id, it.name, it.description, it.createdAt, it.updatedAt) }
    }

    override fun filterByName(name: String): List<Theme> {
        return repository.filterByName(name)
            .map { Theme(it.id, it.name, it.description, it.createdAt, it.updatedAt) }
    }

    override fun create(theme: ThemeEntity): Theme {
        return ThemeEntity.toDomain(repository
            .save(theme))
    }

    override fun update(id: UUID, theme: ThemeEntity): Theme {
        val entity = repository.findById(id)

        if (entity.isEmpty) {
            throw ResourceNotFoundException("Theme with id $id does not exist")
        }

        entity.get().name = theme.name
        entity.get().description = theme.description

        return ThemeEntity.toDomain(repository.save(entity.get()))
    }

}