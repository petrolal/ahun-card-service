package com.petrolal.ahun.ahuncardservice.domain.ports

import com.petrolal.ahun.ahuncardservice.domain.models.Theme

interface ThemeRepositoryPort {

    fun findAll(): List<Theme>

    fun findByName(themeName: String): List<Theme>

    fun create(theme: Theme): Theme

}