package com.petrolal.ahun.ahundutyservice.infrastructure.adapters.inbound.rest

import com.petrolal.ahun.ahundutyservice.application.usecases.DutyUsecase
import com.petrolal.ahun.ahundutyservice.domain.Duty
import com.petrolal.ahun.ahundutyservice.domain.dto.DutyRequestDto
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

/**
 * Inbound REST controller for managing Duty assignments.
 * Exposes endpoints for listing and creating duty assignments.
 */
@Tag(name = "Duty", description = "Registry of duty")
@RestController
@RequestMapping("/duty")
class DutyResource(
    private val dutyUsecase: DutyUsecase,
) {

    /**
     * Endpoint to list all duties, optionally filtered by a theme name query parameter.
     *
     * @param theme Optional keyword filter for the theme's name.
     * @return List of [Duty]s.
     */
    @GetMapping
    fun findAll(@RequestParam(name = "theme", required = false) theme: String?): List<Duty> {
        if (!theme.isNullOrBlank()) {
            return dutyUsecase.findByThemeName(theme)
        }

        return dutyUsecase.findAll()
    }

    /**
     * Endpoint to create a new duty assignment.
     *
     * @param requestDto Details of the duty assignment to create.
     * @return The created [Duty] object.
     */
    @PostMapping
    fun create(@RequestBody requestDto: DutyRequestDto): Duty = dutyUsecase.create(requestDto)
}