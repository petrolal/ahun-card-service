package com.petrolal.ahun.ahuncardservice.domain.ports

import com.petrolal.ahun.ahuncardservice.domain.models.Theme
import com.petrolal.ahun.ahuncardservice.domain.models.dto.PostThemeRequestDto

interface ThemeUsecasePort {

    fun findAll(): List<Theme>

    fun create(themeRequest: PostThemeRequestDto): Theme

}