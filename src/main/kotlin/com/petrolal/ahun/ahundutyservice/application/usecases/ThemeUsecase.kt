package com.petrolal.ahun.ahundutyservice.application.usecases

import com.petrolal.ahun.ahundutyservice.domain.Theme
import com.petrolal.ahun.ahundutyservice.domain.dto.ThemeRequestDto
import com.petrolal.ahun.ahundutyservice.domain.exception.BadRequestException
import com.petrolal.ahun.ahundutyservice.infrastructure.persistence.entity.ThemeEntity
import com.petrolal.ahun.ahundutyservice.infrastructure.ports.ThemeRepositoryPort
import com.petrolal.ahun.ahundutyservice.infrastructure.ports.ThemeUsecasePort
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class ThemeUsecase (
    private val repository: ThemeRepositoryPort
) : ThemeUsecasePort {
    override fun findAll(): List<Theme> {
        return repository.findAll()
    }

    override fun filterByName(name: String): List<Theme> {
        return repository.filterByName(name)
    }

    override fun create(requestDto: ThemeRequestDto): Theme {
        val theme = ThemeEntity(
            id = UUID.randomUUID(),
            name = requestDto.name,
            description = requestDto.description,
            createdAt = LocalDateTime.now(),
            updatedAt = null,
        )

        return repository.create(theme)
    }

    override fun update(id: UUID, requestDto: ThemeRequestDto): Theme {
        if (id.toString().isEmpty()) {
            throw BadRequestException("ID should not be empty")
        }

        val entity = ThemeEntity(
            id = id,
            name = requestDto.name,
            description = requestDto.description,
            createdAt = LocalDateTime.now(),
            updatedAt = null,
        )

        return repository.update(id, entity)
    }
}