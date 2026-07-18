package com.petrolal.ahun.ahuncardservice.infrastructure.ports

import com.petrolal.ahun.ahuncardservice.domain.Theme
import com.petrolal.ahun.ahuncardservice.domain.dto.ThemeRequestDto
import java.util.UUID

interface ThemeRepositoryPort {

    fun findAll(): List<Theme>

    fun filterByName(name: String): List<Theme>

    fun create(theme: Theme): Theme

    fun update(id: UUID, theme: ThemeRequestDto): Theme

}