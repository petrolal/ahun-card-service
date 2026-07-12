package com.petrolal.ahun.ahuncardservice.infrastructure.rest

import com.petrolal.ahun.ahuncardservice.application.usecases.ThemeUsecase
import com.petrolal.ahun.ahuncardservice.domain.models.Theme
import com.petrolal.ahun.ahuncardservice.domain.models.dto.PostThemeRequestDto
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Theme", description = "Theme for the selected Gira and card")
@RestController
@RequestMapping("/themes")
class ThemeResource (
    private val themeUsecase: ThemeUsecase
) {

    @GetMapping
    fun list(): List<Theme> {
        return themeUsecase.findAll()
    }

    @PostMapping
    fun create(themeRequestDto: PostThemeRequestDto): Theme {
        return themeUsecase.create(themeRequestDto)
    }

}