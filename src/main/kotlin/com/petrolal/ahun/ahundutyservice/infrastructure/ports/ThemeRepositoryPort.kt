package com.petrolal.ahun.ahundutyservice.infrastructure.ports

import com.petrolal.ahun.ahundutyservice.domain.Theme
import com.petrolal.ahun.ahundutyservice.domain.dto.ThemeRequestDto
import com.petrolal.ahun.ahundutyservice.infrastructure.persistence.entity.ThemeEntity
import java.util.UUID

interface ThemeRepositoryPort {

    fun findAll(): List<Theme>

    fun filterByName(name: String): List<Theme>

    fun create(theme: ThemeEntity): Theme

    fun update(id: UUID, theme: ThemeEntity): Theme

}