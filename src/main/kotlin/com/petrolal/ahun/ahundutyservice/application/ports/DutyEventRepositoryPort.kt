package com.petrolal.ahun.ahundutyservice.application.ports

import com.petrolal.ahun.ahundutyservice.domain.DutyEvent
import java.util.UUID

interface DutyEventRepositoryPort {
    fun findAll(): List<DutyEvent>
    fun create(events: List<DutyEvent>): List<DutyEvent>
    fun findAllById(ids: List<UUID>): List<DutyEvent>
}
