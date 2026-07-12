package com.petrolal.ahun.ahuncardservice.application.usecases

import com.petrolal.ahun.ahuncardservice.domain.models.Theme
import com.petrolal.ahun.ahuncardservice.domain.models.dto.PostThemeRequestDto
import com.petrolal.ahun.ahuncardservice.domain.ports.ThemeRepositoryPort
import com.petrolal.ahun.ahuncardservice.domain.ports.ThemeUsecasePort
import org.springframework.stereotype.Service
import java.util.*

@Service
class ThemeUsecase (
    private val repository: ThemeRepositoryPort
) : ThemeUsecasePort {
    override fun findAll(): List<Theme> {
        return repository.findAll()
    }

    override fun create(themeRequest: PostThemeRequestDto): Theme {

        val theme = Theme(
            id = UUID.randomUUID(),
            name = themeRequest.name,
            description = themeRequest.description,
        )

        return repository.create(theme)
    }
}