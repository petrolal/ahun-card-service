package com.petrolal.ahun.ahundutyservice.application.usecases

import com.petrolal.ahun.ahundutyservice.application.ports.ThemeRepositoryPort
import com.petrolal.ahun.ahundutyservice.domain.Theme
import com.petrolal.ahun.ahundutyservice.domain.dto.ThemeRequestDto
import com.petrolal.ahun.ahundutyservice.domain.exception.BadRequestException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

/**
 * Application service orchestrating business use cases for [Theme]s.
 * Direct inbound entry point for Web REST controllers.
 */
@Service
@Transactional(readOnly = true)
class ThemeUsecase (
    private val repository: ThemeRepositoryPort
) {
    /**
     * Lists all themes in the system.
     *
     * @return List of all [Theme]s.
     */
    fun findAll(): List<Theme> {
        return repository.findAll()
    }

    /**
     * Filters themes by their name.
     *
     * @param name Theme name keyword to search for.
     * @return List of matching [Theme]s.
     */
    fun filterByName(name: String): List<Theme> {
        return repository.filterByName(name)
    }

    /**
     * Creates a new Theme category.
     *
     * @param requestDto Details of the theme to create.
     * @return The created [Theme] domain model.
     */
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

    /**
     * Updates an existing Theme category.
     *
     * @param id The unique identifier of the theme to update.
     * @param requestDto The updated theme details.
     * @return The updated [Theme] domain model.
     * @throws BadRequestException If the ID parameter is empty.
     */
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