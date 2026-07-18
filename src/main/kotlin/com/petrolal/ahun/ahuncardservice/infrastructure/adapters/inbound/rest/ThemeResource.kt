package com.petrolal.ahun.ahuncardservice.infrastructure.adapters.inbound.rest

import com.petrolal.ahun.ahuncardservice.application.usecases.ThemeUsecase
import com.petrolal.ahun.ahuncardservice.domain.Theme
import com.petrolal.ahun.ahuncardservice.domain.dto.ThemeRequestDto
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.repository.query.Param
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@Tag(name = "Theme", description = "Theme for the selected Gira and card")
@RestController
@RequestMapping("/theme")
class ThemeResource (
    private val themeUsecase: ThemeUsecase
) {

    @GetMapping
    fun list(@Param("name") name: String?): List<Theme> {
        if (name !== null && (name.isNotBlank() || name.isNotEmpty())) {
            return themeUsecase.filterByName(name)
        }

        return themeUsecase.findAll()
    }

    @PostMapping
    fun create(themeRequestDto: ThemeRequestDto): Theme {
        return themeUsecase.create(themeRequestDto)
    }

    @PutMapping
    fun update(@Param("id") id: UUID, themeRequestDto: ThemeRequestDto): Theme {
        return themeUsecase.update(id, themeRequestDto)
    }

}