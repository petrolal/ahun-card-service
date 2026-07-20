package com.petrolal.ahun.ahundutyservice.infrastructure.adapters.inbound.rest

import com.petrolal.ahun.ahundutyservice.application.usecases.DutyEventUsecase
import com.petrolal.ahun.ahundutyservice.domain.DutyEvent
import com.petrolal.ahun.ahundutyservice.domain.dto.DutyEventRequestDto
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

/**
 * Inbound REST controller for managing Duty Events.
 * Exposes endpoints for listing and creating duty events.
 */
@Tag(name = "Duty Events", description = "Programmatically events will happen at Duty")
@RestController
@RequestMapping("duty-events")
class DutyEventResource(
    private val dutyEventUsecase: DutyEventUsecase
) {

    /**
     * Endpoint to list all duty events.
     *
     * @return List of [DutyEvent]s.
     */
    @GetMapping
    fun findAll(): List<DutyEvent> = dutyEventUsecase.findAll()

    /**
     * Endpoint to create one or more new duty events.
     *
     * @param event List of duty event details to create.
     * @return List of newly created [DutyEvent]s.
     */
    @PostMapping
    fun create(@RequestBody event: List<DutyEventRequestDto>): List<DutyEvent> =
        dutyEventUsecase.save(event)
}