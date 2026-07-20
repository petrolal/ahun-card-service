package com.petrolal.ahun.ahundutyservice.infrastructure.adapters.inbound.rest

import com.petrolal.ahun.ahundutyservice.domain.Duty
import com.petrolal.ahun.ahundutyservice.infrastructure.ports.DutyUsecasePort
import org.springframework.data.repository.query.Param
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/duty")
class DutyResource(
    private val dutyUsecase: DutyUsecasePort,
) {

    @GetMapping
    fun findAll(@Param("theme") theme: String?): List<Duty> {
        if (!theme.isNullOrBlank()) {
            return dutyUsecase.findByThemeName(theme)
        }

        return dutyUsecase.findAll()
    }

    @PostMapping
    fun create(@RequestBody duty: Duty): Duty = dutyUsecase.create(duty)

}