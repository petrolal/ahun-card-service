package com.petrolal.ahun.ahundutyservice.infrastructure.ports

import com.petrolal.ahun.ahundutyservice.domain.Duty
import com.petrolal.ahun.ahundutyservice.infrastructure.persistence.entity.DutyEntity

interface DutyRepositoryPort {

    fun findAll(): List<DutyEntity>

    fun findByThemeName(themeName: String): List<DutyEntity>

    fun create(duty: Duty): DutyEntity

}
