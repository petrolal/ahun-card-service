package com.petrolal.ahun.ahuncardservice.infrastructure.persistence.repository

import com.petrolal.ahun.ahuncardservice.infrastructure.persistence.entity.GiraEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface GiraRepositoryJpa : JpaRepository<GiraEntity, UUID> {

    fun findByThemeName(themeName: String): List<GiraEntity>

}
