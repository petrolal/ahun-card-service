package com.petrolal.ahun.ahundutyservice.infrastructure.ports

import com.petrolal.ahun.ahundutyservice.domain.Duty

interface DutyUsecasePort {

    fun findAll(): List<Duty>

    fun findByThemeName(themeName: String): List<Duty>

    fun create(duty: Duty): Duty

}