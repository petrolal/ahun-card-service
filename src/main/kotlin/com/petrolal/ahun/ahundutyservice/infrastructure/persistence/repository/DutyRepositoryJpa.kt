package com.petrolal.ahun.ahundutyservice.infrastructure.persistence.repository

import com.petrolal.ahun.ahundutyservice.infrastructure.persistence.entity.DutyEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface DutyRepositoryJpa : JpaRepository<DutyEntity, UUID> {

    @Query("SELECT d FROM DutyEntity d LEFT JOIN FETCH d.theme LEFT JOIN FETCH d.events WHERE d.theme.name like %:themeName%")
    fun findByThemeName(@Param("themeName") themeName: String): List<DutyEntity>

    @Query("SELECT d FROM DutyEntity d LEFT JOIN FETCH d.theme LEFT JOIN FETCH d.events")
    override fun findAll(): List<DutyEntity>

}
