package com.petrolal.ahun.ahundutyservice.infrastructure.adapters.inbound.rest

import com.petrolal.ahun.ahundutyservice.application.usecases.ThemeUsecase
import com.petrolal.ahun.ahundutyservice.domain.Theme
import com.petrolal.ahun.ahundutyservice.domain.dto.ThemeRequestDto
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.util.*

@Tag(name = "Theme", description = "Theme for the selected Duty and card")
@RestController
@RequestMapping("theme")
class ThemeResource(
    private val themeUsecase: ThemeUsecase
) {

    @GetMapping
    fun list(@RequestParam(name = "name", required = false) name: String?): List<Theme> {
        if (!name.isNullOrEmpty()) {
            return themeUsecase.filterByName(name)
        }
        return themeUsecase.findAll()
    }

    @PostMapping
    fun create(@RequestBody themeRequestDto: ThemeRequestDto): Theme =
        themeUsecase.create(themeRequestDto)

    @PutMapping
    fun update(@RequestParam("id") id: UUID, @RequestBody themeRequestDto: ThemeRequestDto): Theme =
        themeUsecase.update(id, themeRequestDto)

}