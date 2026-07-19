package com.petrolal.ahun.ahundutyservice.infrastructure.persistence.repository

import com.petrolal.ahun.ahundutyservice.domain.Duty
import com.petrolal.ahun.ahundutyservice.domain.DutyEvent
import com.petrolal.ahun.ahundutyservice.domain.DutyTypeEnum
import com.petrolal.ahun.ahundutyservice.domain.Theme
import com.petrolal.ahun.ahundutyservice.infrastructure.ports.DutyRepositoryPort
import org.springframework.stereotype.Repository

@Repository
class DutyRepository(
    private val repository: DutyRepositoryJpa
) : DutyRepositoryPort {

    override fun findByThemeName(themeName: String): List<Duty> {
        if (themeName.isBlank()) {
            throw IllegalArgumentException("Theme name cannot be blank")
        }

        return repository.findByThemeName(themeName)
            .map { entity ->
                Duty(
                    id = entity.id,
                    theme = Theme(
                        id = entity.theme.id,
                        name = entity.theme.name,
                        description = entity.theme.description
                    ),
                    dutyType = DutyTypeEnum.valueOf(entity.dutyType.name),
                    date = entity.date,
                    period = entity.period,
                    description = entity.description,
                    year = entity.year,
                    events = entity.events.map { event ->
                        DutyEvent(
                            id = event.id,
                            name = event.name,
                            startedAt = event.startedAt,
                            description = event.description,
                            visibleInCard = event.visibleInCard
                        )
                    }.toMutableList()
                )
            }
    }
}
