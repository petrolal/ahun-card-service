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

@Service
@Transactional(readOnly = true)
class DutyUsecase (
    private val repository: DutyRepositoryPort,
    private val repositoryTheme: ThemeRepositoryPort,
    private val repositoryDutyEvent: DutyEventRepositoryPort
) {
    fun findByThemeName(themeName: String): List<Duty> =
        repository.findByThemeName(themeName)

    fun findAll(): List<Duty> =
        repository.findAll()

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