package com.petrolal.ahun.ahuncardservice.infrastructure.ports

import com.petrolal.ahun.ahuncardservice.domain.GiraEvent
import com.petrolal.ahun.ahuncardservice.domain.dto.GiraEventRequestDto

interface GiraEventUsecasePort {

    fun findAll(): List<GiraEvent>

    fun save(events: List<GiraEventRequestDto>): List<GiraEvent>

}