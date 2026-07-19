package com.petrolal.ahun.ahuncardservice.infrastructure.adapters.inbound.rest

import com.petrolal.ahun.ahuncardservice.application.usecases.GiraEventUsecase
import com.petrolal.ahun.ahuncardservice.domain.GiraEvent
import com.petrolal.ahun.ahuncardservice.domain.dto.GiraEventRequestDto
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "Gira Events", description = "Programmatically events will happen at Gira")
@RestController
@RequestMapping("gira-events")
class GiraEventResource(
    private val giraEventUsecase: GiraEventUsecase
) {

    @GetMapping
    fun findAll(): List<GiraEvent> = giraEventUsecase.findAll()

    @PostMapping
    fun create(@RequestBody event: List<GiraEventRequestDto>): List<GiraEvent> =
        giraEventUsecase.save(event)

}