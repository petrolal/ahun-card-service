package com.petrolal.ahun.ahundutyservice.application.usecases

import com.petrolal.ahun.ahundutyservice.domain.Duty
import com.petrolal.ahun.ahundutyservice.infrastructure.persistence.entity.DutyEntity
import com.petrolal.ahun.ahundutyservice.infrastructure.persistence.entity.DutyEventEntity
import com.petrolal.ahun.ahundutyservice.infrastructure.persistence.entity.ThemeEntity
import com.petrolal.ahun.ahundutyservice.infrastructure.ports.DutyEventRepositoryPort
import com.petrolal.ahun.ahundutyservice.infrastructure.ports.DutyRepositoryPort
import com.petrolal.ahun.ahundutyservice.infrastructure.ports.DutyUsecasePort
import com.petrolal.ahun.ahundutyservice.infrastructure.ports.ThemeRepositoryPort
import org.springframework.stereotype.Service

@Service
class DutyUsecase (
    private val repository: DutyRepositoryPort,
    private val repositoryTheme: ThemeRepositoryPort,
    private val repositoryDutyEvent: DutyEventRepositoryPort
) : DutyUsecasePort {
    override fun findByThemeName(themeName: String): List<Duty> =
        repository.findByThemeName(themeName)
            .map(DutyEntity::toDomain)

    override fun findAll(): List<Duty> =
        repository.findAll()
            .map(DutyEntity::toDomain)

    override fun create(duty: Duty): Duty {

        val theme = repositoryTheme.create(ThemeEntity.toEntity(duty.theme))
        val events = repositoryDutyEvent.create(duty.events.map { DutyEventEntity.toEntity(it) })

        duty.theme = theme
        duty.events = events.map(DutyEventEntity::toDomain)
            .toMutableList()

        val created = repository.create(duty)
        return DutyEntity.toDomain(created)
    }
}