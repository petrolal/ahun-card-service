package com.petrolal.ahun.ahundutyservice.infrastructure.adapters.inbound.rest

import com.petrolal.ahun.ahundutyservice.application.usecases.DutyEventUsecase
import com.petrolal.ahun.ahundutyservice.domain.DutyEvent
import com.petrolal.ahun.ahundutyservice.domain.dto.DutyEventRequestDto
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "Duty Events", description = "Programmatically events will happen at Duty")
@RestController
@RequestMapping("duty-events")
class DutyEventResource(
    private val dutyEventUsecase: DutyEventUsecase
) {

    @GetMapping
    fun findAll(): List<DutyEvent> = dutyEventUsecase.findAll()

    @PostMapping
    fun create(@RequestBody event: List<DutyEventRequestDto>): List<DutyEvent> =
        dutyEventUsecase.save(event)

}