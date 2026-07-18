package com.petrolal.ahun.ahuncardservice.infrastructure.persistence.repository

import com.petrolal.ahun.ahuncardservice.domain.Theme
import com.petrolal.ahun.ahuncardservice.domain.dto.ThemeRequestDto
import com.petrolal.ahun.ahuncardservice.domain.exception.ResourceNotFoundException
import com.petrolal.ahun.ahuncardservice.infrastructure.ports.ThemeRepositoryPort
import com.petrolal.ahun.ahuncardservice.infrastructure.persistence.entity.ThemeEntity
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class ThemeRepository(
    private val repository: ThemeRepositoryJpa
) : ThemeRepositoryPort {
    override fun findAll(): List<Theme> {
        return repository.findAll()
            .map { Theme(it.id, it.name, it.description) }
    }

    override fun filterByName(name: String): List<Theme> {
        return repository.filterByName(name)
            .map { Theme(it.id, it.name, it.description) }
    }

    override fun create(theme: Theme): Theme {
        val entity = repository.save(ThemeEntity.toEntity(theme))
        return ThemeEntity.toDomain(entity)
    }

    override fun update(id: UUID, theme: ThemeRequestDto): Theme {
        val entity = repository.findById(id)

        if (entity.isEmpty) {
            throw ResourceNotFoundException("Theme with id $id does not exist")
        }

        entity.get().name = theme.name
        entity.get().description = theme.description

        return ThemeEntity.toDomain(repository.save(entity.get()))
    }

}