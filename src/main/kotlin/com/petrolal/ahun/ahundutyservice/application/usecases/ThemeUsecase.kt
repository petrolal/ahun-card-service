package com.petrolal.ahun.ahundutyservice.application.usecases

import com.petrolal.ahun.ahundutyservice.application.ports.ThemeRepositoryPort
import com.petrolal.ahun.ahundutyservice.domain.Theme
import com.petrolal.ahun.ahundutyservice.domain.dto.ThemeRequestDto
import com.petrolal.ahun.ahundutyservice.domain.exception.BadRequestException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
@Transactional(readOnly = true)
class ThemeUsecase (
    private val repository: ThemeRepositoryPort
) {
    fun findAll(): List<Theme> {
        return repository.findAll()
    }

    fun filterByName(name: String): List<Theme> {
        return repository.filterByName(name)
    }

    @Transactional
    fun create(requestDto: ThemeRequestDto): Theme {
        val theme = Theme(
            id = UUID.randomUUID(),
            name = requestDto.name,
            description = requestDto.description,
            createdAt = LocalDateTime.now(),
            updatedAt = null,
        )

        return repository.create(theme)
    }

    @Transactional
    fun update(id: UUID, requestDto: ThemeRequestDto): Theme {
        if (id.toString().isEmpty()) {
            throw BadRequestException("ID should not be empty")
        }

        val theme = Theme(
            id = id,
            name = requestDto.name,
            description = requestDto.description,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
        )

        return repository.update(id, theme)
    }
}