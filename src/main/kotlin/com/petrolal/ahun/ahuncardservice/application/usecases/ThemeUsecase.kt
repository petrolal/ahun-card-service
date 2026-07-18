package com.petrolal.ahun.ahuncardservice.application.usecases

import com.petrolal.ahun.ahuncardservice.domain.Theme
import com.petrolal.ahun.ahuncardservice.domain.dto.ThemeRequestDto
import com.petrolal.ahun.ahuncardservice.domain.exception.BadRequestException
import com.petrolal.ahun.ahuncardservice.infrastructure.ports.ThemeRepositoryPort
import com.petrolal.ahun.ahuncardservice.infrastructure.ports.ThemeUsecasePort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
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
        val theme = Theme(
            id = UUID.randomUUID(),
            name = requestDto.name,
            description = requestDto.description,
        )

        return repository.create(theme)
    }

    override fun update(id: UUID, requestDto: ThemeRequestDto): Theme {
        if (id.toString().isEmpty()) {
            throw BadRequestException("ID should not be empty")
        }

        return repository.update(id, requestDto)
    }
}