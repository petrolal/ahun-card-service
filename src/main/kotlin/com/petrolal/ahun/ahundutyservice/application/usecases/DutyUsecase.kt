package com.petrolal.ahun.ahundutyservice.application.usecases

import com.petrolal.ahun.ahundutyservice.application.ports.DutyEventRepositoryPort
import com.petrolal.ahun.ahundutyservice.application.ports.DutyRepositoryPort
import com.petrolal.ahun.ahundutyservice.application.ports.ThemeRepositoryPort
import com.petrolal.ahun.ahundutyservice.domain.Duty
import com.petrolal.ahun.ahundutyservice.domain.dto.DutyRequestDto
import com.petrolal.ahun.ahundutyservice.domain.exception.ResourceNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

/**
 * Application service orchestrating business use cases for [Duty]s.
 * Direct inbound entry point for Web REST controllers.
 */
@Service
@Transactional(readOnly = true)
class DutyUsecase (
    private val repository: DutyRepositoryPort,
    private val repositoryTheme: ThemeRepositoryPort,
    private val repositoryDutyEvent: DutyEventRepositoryPort
) {
    /**
     * Finds all duties associated with a specific theme name.
     *
     * @param themeName Theme name to query.
     * @return List of matching [Duty] domain models.
     */
    fun findByThemeName(themeName: String): List<Duty> =
        repository.findByThemeName(themeName)

    /**
     * Lists all duties in the system.
     *
     * @return List of all [Duty] domain models.
     */
    fun findAll(): List<Duty> =
        repository.findAll()

    /**
     * Creates a new Duty assignment referencing an existing theme and multiple duty events.
     *
     * @param requestDto Details of the duty assignment, including theme and event IDs.
     * @return The created [Duty] domain model.
     * @throws ResourceNotFoundException If the referenced Theme or any referenced Event is not found.
     */
    @Transactional
    fun create(requestDto: DutyRequestDto): Duty {
        val theme = repositoryTheme.findById(requestDto.themeId)
            ?: throw ResourceNotFoundException("Theme with id ${requestDto.themeId} not found")

        val events = repositoryDutyEvent.findAllById(requestDto.eventIds)
        if (events.size != requestDto.eventIds.size) {
            throw ResourceNotFoundException("One or more events not found")
        }

        val duty = Duty(
            id = UUID.randomUUID(),
            theme = theme,
            dutyType = requestDto.dutyType,
            date = requestDto.date,
            period = requestDto.period,
            description = requestDto.description,
            year = requestDto.year,
            events = events.toMutableSet(),
            createdAt = LocalDateTime.now(),
            updatedAt = null
        )

        return repository.create(duty)
    }
}