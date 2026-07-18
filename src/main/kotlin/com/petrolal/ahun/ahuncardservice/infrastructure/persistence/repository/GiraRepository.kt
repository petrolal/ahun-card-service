package com.petrolal.ahun.ahuncardservice.infrastructure.persistence.repository

import com.petrolal.ahun.ahuncardservice.domain.Gira
import com.petrolal.ahun.ahuncardservice.domain.Theme
import com.petrolal.ahun.ahuncardservice.domain.GiraEvent
import com.petrolal.ahun.ahuncardservice.infrastructure.ports.GiraRepositoryPort
import org.springframework.stereotype.Repository

@Repository
class GiraRepository(
    private val repository: GiraRepositoryJpa
) : GiraRepositoryPort {

    override fun findByThemeName(themeName: String): List<Gira> {
        if (themeName.isBlank()) {
            throw IllegalArgumentException("Theme name cannot be blank")
        }

        return repository.findByThemeName(themeName)
            .map { entity ->
                Gira(
                    id = entity.id,
                    theme = Theme(
                        id = entity.theme.id,
                        name = entity.theme.name,
                        description = entity.theme.description
                    ),
                    date = entity.date,
                    period = entity.period,
                    description = entity.description,
                    year = entity.year,
                    events = entity.events.map { event ->
                        GiraEvent(
                            id = event.id,
                            name = event.name,
                            startedAt = event.startedAt,
                            visibleInCard = event.visibleInCard
                        )
                    }.toMutableList()
                )
            }
    }
}
