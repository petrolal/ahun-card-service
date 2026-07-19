package com.petrolal.ahun.ahundutyservice.infrastructure.ports

import com.petrolal.ahun.ahundutyservice.domain.DutyEvent
import com.petrolal.ahun.ahundutyservice.domain.dto.DutyEventRequestDto

interface DutyEventRepositoryPort {

    fun findAll(): List<DutyEvent>

    fun save(events: List<DutyEventRequestDto>): List<DutyEvent>

}