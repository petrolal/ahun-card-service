package com.petrolal.ahun.ahundutyservice.infrastructure.adapters.inbound.rest

import com.petrolal.ahun.ahundutyservice.application.usecases.DutyUsecase
import com.petrolal.ahun.ahundutyservice.domain.Duty
import com.petrolal.ahun.ahundutyservice.domain.DutyTypeEnum
import com.petrolal.ahun.ahundutyservice.domain.dto.DutyRequestDto
import com.petrolal.ahun.ahundutyservice.infrastructure.adapters.inbound.rest.assembler.DutyModelAssembler
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

/**
 * Inbound REST controller for managing Duty assignments.
 * Exposes endpoints for listing, finding, and creating duty assignments with HATEOAS hypermedia controls.
 */
@Tag(name = "Duty", description = "The endpoint to manage duties")
@RestController
@RequestMapping("/duty")
class DutyResource(
    private val dutyUsecase: DutyUsecase,
    private val dutyModelAssembler: DutyModelAssembler
) {

    /**
     * Endpoint to list all duties, optionally filtered by a theme name and/or a duty type query parameter.
     * Returns a HAL compliant [CollectionModel] containing hypermedia links for each duty.
     *
     * @param theme Optional keyword filter for the theme's name.
     * @param dutyType Optional filter for the duty type.
     * @return HATEOAS [CollectionModel] of [Duty] entities.
     */
    @GetMapping
    fun findAll(
        @RequestParam(name = "theme", required = false) theme: String?,
        @RequestParam(name = "dutyType", required = false) dutyType: DutyTypeEnum?
    ): CollectionModel<EntityModel<Duty>> {
        val duties = when {
            !theme.isNullOrBlank() && dutyType != null -> dutyUsecase.findByThemeNameAndDutyType(theme, dutyType)
            !theme.isNullOrBlank() -> dutyUsecase.findByThemeName(theme)
            dutyType != null -> dutyUsecase.findByDutyType(dutyType)
            else -> dutyUsecase.findAll()
        }

        return dutyModelAssembler.toCollectionModel(duties)
    }

    /**
     * Endpoint to get a specific duty by ID.
     * Returns a HAL compliant [EntityModel] with hypermedia links for self, card render, and card preview.
     *
     * @param id The duty UUID.
     * @return HATEOAS [EntityModel] of the [Duty] object.
     */
    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: UUID): EntityModel<Duty> {
        val duty = dutyUsecase.findById(id)
        return dutyModelAssembler.toModel(duty)
    }

    /**
     * Endpoint to create a new duty assignment.
     * Returns HTTP 201 Created with a Location header and HATEOAS HAL representation.
     *
     * @param requestDto Details of the duty assignment to create.
     * @return ResponseEntity containing the created HATEOAS [EntityModel] and Location header.
     */
    @PostMapping
    fun create(@RequestBody requestDto: DutyRequestDto): ResponseEntity<EntityModel<Duty>> {
        val createdDuty = dutyUsecase.create(requestDto)
        val entityModel = dutyModelAssembler.toModel(createdDuty)

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel)
    }

    /**
     * Endpoint to update an existing duty assignment using a path variable ID.
     *
     * @param id The duty UUID.
     * @param requestDto The updated duty details.
     * @return HATEOAS [EntityModel] of the updated [Duty] object.
     */
    @PutMapping("/{id}")
    fun update(
        @PathVariable("id") id: UUID,
        @RequestBody requestDto: DutyRequestDto
    ): EntityModel<Duty> {
        val updatedDuty = dutyUsecase.update(id, requestDto)
        return dutyModelAssembler.toModel(updatedDuty)
    }

    /**
     * Endpoint to update an existing duty assignment using a query parameter ID.
     *
     * @param id The duty UUID.
     * @param requestDto The updated duty details.
     * @return HATEOAS [EntityModel] of the updated [Duty] object.
     */
    @PutMapping
    fun updateWithParam(
        @RequestParam("id") id: UUID,
        @RequestBody requestDto: DutyRequestDto
    ): EntityModel<Duty> {
        return update(id, requestDto)
    }
}