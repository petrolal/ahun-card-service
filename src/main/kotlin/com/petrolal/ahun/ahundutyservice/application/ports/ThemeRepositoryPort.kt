package com.petrolal.ahun.ahundutyservice.application.ports

import com.petrolal.ahun.ahundutyservice.domain.Theme
import java.util.UUID

interface ThemeRepositoryPort {
    fun findAll(): List<Theme>
    fun filterByName(name: String): List<Theme>
    fun create(theme: Theme): Theme
    fun update(id: UUID, theme: Theme): Theme
    fun findById(id: UUID): Theme?
}
