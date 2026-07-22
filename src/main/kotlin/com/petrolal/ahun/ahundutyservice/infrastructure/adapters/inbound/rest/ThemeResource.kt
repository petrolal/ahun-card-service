package com.petrolal.ahun.ahundutyservice.infrastructure.adapters.inbound.rest

import com.petrolal.ahun.ahundutyservice.application.usecases.ThemeUsecase
import com.petrolal.ahun.ahundutyservice.domain.Theme
import com.petrolal.ahun.ahundutyservice.domain.dto.ThemeRequestDto
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * Inbound REST controller for managing Themes.
 * Exposes endpoints for listing, creating, and updating themes.
 */
@Tag(name = "Theme", description = "Theme for the selected Duty and card")
@RestController
@RequestMapping("theme")
class ThemeResource(
    private val themeUsecase: ThemeUsecase
) {

    /**
     * Endpoint to list all themes, optionally filtered by a name query parameter.
     *
     * @param name Optional keyword filter for the theme's name.
     * @return List of [Theme]s.
     */
    @GetMapping
    fun list(@RequestParam(name = "name", required = false) name: String?): List<Theme> {
        if (!name.isNullOrEmpty()) {
            return themeUsecase.filterByName(name)
        }
        return themeUsecase.findAll()
    }

    /**
     * Endpoint to get a specific theme by ID.
     *
     * @param id The theme UUID.
     * @return The [Theme] object.
     */
    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: UUID): Theme =
        themeUsecase.findById(id)

    /**
     * Endpoint to create a new theme.
     *
     * @param themeRequestDto Details of the theme to create.
     * @return The created [Theme] object.
     */
    @PostMapping
    fun create(@RequestBody themeRequestDto: ThemeRequestDto): Theme =
        themeUsecase.create(themeRequestDto)

    /**
     * Endpoint to update an existing theme using path variable.
     *
     * @param id The theme UUID.
     * @param themeRequestDto The updated theme values.
     * @return The updated [Theme] object.
     */
    @PutMapping("/{id}")
    fun updateForPath(@PathVariable("id") id: UUID, @RequestBody themeRequestDto: ThemeRequestDto): Theme =
        themeUsecase.update(id, themeRequestDto)

    /**
     * Endpoint to update an existing theme using query parameter.
     *
     * @param id The theme UUID.
     * @param themeRequestDto The updated theme values.
     * @return The updated [Theme] object.
     */
    @PutMapping
    fun update(@RequestParam("id") id: UUID, @RequestBody themeRequestDto: ThemeRequestDto): Theme =
        themeUsecase.update(id, themeRequestDto)
}