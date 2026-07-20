package com.petrolal.ahun.ahundutyservice.infrastructure.ports

import com.petrolal.ahun.ahundutyservice.domain.DutyEvent
import com.petrolal.ahun.ahundutyservice.infrastructure.persistence.entity.DutyEventEntity

interface DutyEventRepositoryPort {

    fun findAll(): List<DutyEventEntity>

    fun create(events: List<DutyEventEntity>): List<DutyEventEntity>

}