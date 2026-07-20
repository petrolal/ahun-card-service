package com.petrolal.ahun.ahundutyservice.infrastructure.persistence.repository

import com.petrolal.ahun.ahundutyservice.domain.Duty
import com.petrolal.ahun.ahundutyservice.domain.DutyTypeEnum
import com.petrolal.ahun.ahundutyservice.infrastructure.persistence.entity.DutyEntity
import com.petrolal.ahun.ahundutyservice.infrastructure.persistence.entity.DutyEventEntity
import com.petrolal.ahun.ahundutyservice.infrastructure.persistence.entity.ThemeEntity
import com.petrolal.ahun.ahundutyservice.infrastructure.ports.DutyRepositoryPort
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class DutyRepository(
    private val repository: DutyRepositoryJpa
) : DutyRepositoryPort {

    override fun findByThemeName(themeName: String): List<DutyEntity> {
        if (themeName.isBlank()) {
            throw IllegalArgumentException("Theme name cannot be blank")
        }

        return repository.findByThemeName(themeName)
            .map { entity ->
                DutyEntity(
                    id = entity.id,
                    theme = ThemeEntity(
                        id = entity.theme.id,
                        name = entity.theme.name,
                        description = entity.theme.description,
                        createdAt = entity.theme.createdAt,
                        updatedAt = entity.theme.updatedAt,
                    ),
                    dutyType = DutyTypeEnum.valueOf(entity.dutyType.name),
                    date = entity.date,
                    period = entity.period,
                    description = entity.description,
                    year = entity.year,
                    events = entity.events.map { event ->
                        DutyEventEntity(
                            id = event.id,
                            name = event.name,
                            startedAt = event.startedAt,
                            description = event.description,
                            visibleInCard = event.visibleInCard,
                            createdAt = event.createdAt,
                            updatedAt = event.updatedAt,
                        )
                    }.toMutableList(),
                    createdAt = entity.createdAt,
                    updatedAt = entity.updatedAt,
                )
            }
    }

    override fun create(duty: Duty): DutyEntity {
        val entity = DutyEntity(
            id = UUID.randomUUID(),
            theme = ThemeEntity.toEntity(duty.theme),
            dutyType = DutyTypeEnum.valueOf(duty.dutyType.name),
            date = duty.date,
            period = duty.period,
            description = duty.description,
            year = duty.year,
            events = duty.events.map { DutyEventEntity.toEntity(it) }.toMutableList(),
            createdAt = duty.createdAt,
            updatedAt = duty.updatedAt,
            )
        return repository.save(entity)
    }

    override fun findAll(): List<DutyEntity> {
        return repository.findAll()
    }
}
