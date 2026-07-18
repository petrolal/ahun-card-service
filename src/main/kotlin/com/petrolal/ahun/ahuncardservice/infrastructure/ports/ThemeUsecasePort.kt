package com.petrolal.ahun.ahuncardservice.infrastructure.ports

import com.petrolal.ahun.ahuncardservice.domain.Theme
import com.petrolal.ahun.ahuncardservice.domain.dto.ThemeRequestDto
import java.util.UUID

interface ThemeUsecasePort {

    fun findAll(): List<Theme>

    fun filterByName(name: String): List<Theme>

    fun create(requestDto: ThemeRequestDto): Theme

    fun update(id: UUID, requestDto: ThemeRequestDto): Theme

}