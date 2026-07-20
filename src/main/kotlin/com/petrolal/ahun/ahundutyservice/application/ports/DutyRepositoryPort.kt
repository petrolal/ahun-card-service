package com.petrolal.ahun.ahundutyservice.application.ports

import com.petrolal.ahun.ahundutyservice.domain.Duty

interface DutyRepositoryPort {
    fun findAll(): List<Duty>
    fun findByThemeName(themeName: String): List<Duty>
    fun create(duty: Duty): Duty
}
