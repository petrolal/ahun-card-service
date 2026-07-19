package com.petrolal.ahun.ahuncardservice.application.usecases

import com.petrolal.ahun.ahuncardservice.domain.GiraEvent
import com.petrolal.ahun.ahuncardservice.domain.dto.GiraEventRequestDto
import com.petrolal.ahun.ahuncardservice.infrastructure.ports.GiraEventRepositoryPort
import com.petrolal.ahun.ahuncardservice.infrastructure.ports.GiraEventUsecasePort
import org.apache.coyote.BadRequestException
import org.springframework.stereotype.Service

@Service
class GiraEventUsecase(
    private val repository: GiraEventRepositoryPort,
) : GiraEventUsecasePort {
    override fun findAll(): List<GiraEvent> {
        return repository.findAll()
    }

    override fun save(events: List<GiraEventRequestDto>): List<GiraEvent> {

        if (events.isEmpty()) {
            throw BadRequestException("Events must not be empty")
        }

        return repository.save(events)
    }
}