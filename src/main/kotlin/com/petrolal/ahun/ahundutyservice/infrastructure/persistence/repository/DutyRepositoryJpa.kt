package com.petrolal.ahun.ahundutyservice.infrastructure.persistence.repository

import com.petrolal.ahun.ahundutyservice.infrastructure.persistence.entity.DutyEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface DutyRepositoryJpa : JpaRepository<DutyEntity, UUID> {

    fun findByThemeName(themeName: String): List<DutyEntity>

}
