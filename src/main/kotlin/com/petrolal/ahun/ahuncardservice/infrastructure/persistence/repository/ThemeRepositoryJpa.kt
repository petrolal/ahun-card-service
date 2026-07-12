package com.petrolal.ahun.ahuncardservice.infrastructure.persistence.repository

import com.petrolal.ahun.ahuncardservice.infrastructure.persistence.entity.ThemeEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ThemeRepositoryJpa : JpaRepository<ThemeEntity, UUID> {

    fun findByName(name: String): List<ThemeEntity>

}