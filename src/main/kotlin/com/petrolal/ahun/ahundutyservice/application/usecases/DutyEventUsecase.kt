package com.petrolal.ahun.ahundutyservice.application.usecases

import com.petrolal.ahun.ahundutyservice.domain.DutyEvent
import com.petrolal.ahun.ahundutyservice.domain.dto.DutyEventRequestDto
import com.petrolal.ahun.ahundutyservice.infrastructure.persistence.entity.DutyEventEntity
import com.petrolal.ahun.ahundutyservice.infrastructure.ports.DutyEventRepositoryPort
import com.petrolal.ahun.ahundutyservice.infrastructure.ports.DutyEventUsecasePort
import org.apache.coyote.BadRequestException
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class DutyEventUsecase(
    private val repository: DutyEventRepositoryPort,
) : DutyEventUsecasePort {

    override fun findAll(): List<DutyEvent>  =
        repository.findAll()
            .map(DutyEventEntity::toDomain)

    /**
     *  @return List<DutyEvent>
     *  @param events
     */
    override fun save(events: List<DutyEventRequestDto>): List<DutyEvent> {

        if (events.isEmpty()) {
            throw BadRequestException("Events must not be empty")
        }

        val entity = events.map {
            DutyEventEntity(
                id = UUID.randomUUID(),
                name = it.name,
                startedAt = it.startedAt,
                visibleInCard = it.visibleInCard,
                description = it.description,
                createdAt = LocalDateTime.now(),
                updatedAt = null
            )
        }

        return repository.create(entity)
            .map(DutyEventEntity::toDomain)
    }
}