package com.petrolal.ahun.ahundutyservice.infrastructure.ports

import com.petrolal.ahun.ahundutyservice.domain.Duty

interface DutyRepositoryPort {

    fun findByThemeName(themeName: String): List<Duty>

}
