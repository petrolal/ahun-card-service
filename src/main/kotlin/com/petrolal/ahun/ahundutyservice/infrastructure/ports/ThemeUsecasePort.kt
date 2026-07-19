package com.petrolal.ahun.ahundutyservice.infrastructure.ports

import com.petrolal.ahun.ahundutyservice.domain.Theme
import com.petrolal.ahun.ahundutyservice.domain.dto.ThemeRequestDto
import java.util.UUID

interface ThemeUsecasePort {

    fun findAll(): List<Theme>

    fun filterByName(name: String): List<Theme>

    fun create(requestDto: ThemeRequestDto): Theme

    fun update(id: UUID, requestDto: ThemeRequestDto): Theme

}