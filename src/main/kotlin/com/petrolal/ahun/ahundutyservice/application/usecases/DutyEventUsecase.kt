package com.petrolal.ahun.ahundutyservice.application.usecases

import com.petrolal.ahun.ahundutyservice.application.ports.DutyEventRepositoryPort
import com.petrolal.ahun.ahundutyservice.domain.DutyEvent
import com.petrolal.ahun.ahundutyservice.domain.dto.DutyEventRequestDto
import com.petrolal.ahun.ahundutyservice.domain.exception.BadRequestException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

/**
 * Application service orchestrating business use cases for [DutyEvent]s.
 * Direct inbound entry point for Web REST controllers.
 */
@Service
@Transactional(readOnly = true)
class DutyEventUsecase(
    private val repository: DutyEventRepositoryPort,
) {

    /**
     * Lists all duty events in the system.
     *
     * @return List of all [DutyEvent]s.
     */
    fun findAll(): List<DutyEvent> = repository.findAll()

    /**
     * Creates and saves a list of new duty events.
     *
     * @param events The list of [DutyEventRequestDto]s to create.
     * @return List of newly persisted [DutyEvent] domain models.
     * @throws BadRequestException If the input events list is empty.
     */
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