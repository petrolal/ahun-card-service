package com.petrolal.ahun.ahundutyservice.infrastructure.adapters.inbound.rest

import com.petrolal.ahun.ahundutyservice.application.usecases.DutyUsecase
import com.petrolal.ahun.ahundutyservice.domain.Duty
import com.petrolal.ahun.ahundutyservice.domain.dto.DutyRequestDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/duty")
class DutyResource(
    private val dutyUsecase: DutyUsecase,
) {

    @GetMapping
    fun findAll(@RequestParam(name = "theme", required = false) theme: String?): List<Duty> {
        if (!theme.isNullOrBlank()) {
            return dutyUsecase.findByThemeName(theme)
        }

        return dutyUsecase.findAll()
    }

    @PostMapping
    fun create(@RequestBody requestDto: DutyRequestDto): Duty = dutyUsecase.create(requestDto)

}