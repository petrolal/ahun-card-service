package com.petrolal.ahun.ahundutyservice.application.usecases

import com.petrolal.ahun.ahundutyservice.domain.DutyEvent
import com.petrolal.ahun.ahundutyservice.domain.dto.DutyEventRequestDto
import com.petrolal.ahun.ahundutyservice.infrastructure.ports.DutyEventRepositoryPort
import com.petrolal.ahun.ahundutyservice.infrastructure.ports.DutyEventUsecasePort
import org.apache.coyote.BadRequestException
import org.springframework.stereotype.Service

@Service
class DutyEventUsecase(
    private val repository: DutyEventRepositoryPort,
) : DutyEventUsecasePort {
    override fun findAll(): List<DutyEvent> {
        return repository.findAll()
    }

    override fun save(events: List<DutyEventRequestDto>): List<DutyEvent> {

        if (events.isEmpty()) {
            throw BadRequestException("Events must not be empty")
        }

        return repository.save(events)
    }
}