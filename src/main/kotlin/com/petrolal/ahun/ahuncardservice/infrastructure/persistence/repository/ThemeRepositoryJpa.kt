package com.petrolal.ahun.ahuncardservice.infrastructure.persistence.repository

import com.petrolal.ahun.ahuncardservice.infrastructure.persistence.entity.ThemeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface ThemeRepositoryJpa : JpaRepository<ThemeEntity, UUID> {

    @Query("select u from ThemeEntity u where u.name like %:name%")
    fun filterByName(@Param("name") name: String): List<ThemeEntity>

}