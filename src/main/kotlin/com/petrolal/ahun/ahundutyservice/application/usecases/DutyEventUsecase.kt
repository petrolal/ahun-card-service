package com.petrolal.ahun.ahundutyservice.application.usecases

import com.petrolal.ahun.ahundutyservice.application.ports.DutyEventRepositoryPort
import com.petrolal.ahun.ahundutyservice.domain.DutyEvent
import com.petrolal.ahun.ahundutyservice.domain.dto.DutyEventRequestDto
import com.petrolal.ahun.ahundutyservice.domain.exception.BadRequestException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
@Transactional(readOnly = true)
class DutyEventUsecase(
    private val repository: DutyEventRepositoryPort,
) {

    fun findAll(): List<DutyEvent> = repository.findAll()

    @Transactional
    fun save(events: List<DutyEventRequestDto>): List<DutyEvent> {
        if (events.isEmpty()) {
            throw BadRequestException("Events must not be empty")
        }

        val domainEvents = events.map {
            DutyEvent(
                id = UUID.randomUUID(),
                name = it.name,
                startedAt = it.startedAt,
                visibleInCard = it.visibleInCard,
                description = it.description,
                createdAt = LocalDateTime.now(),
                updatedAt = null
            )
        }

        return repository.create(domainEvents)
    }
}