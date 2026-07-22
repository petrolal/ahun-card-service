package com.petrolal.ahun.ahundutyservice.infrastructure.persistence.repository

import com.petrolal.ahun.ahundutyservice.domain.DutyTypeEnum
import com.petrolal.ahun.ahundutyservice.infrastructure.persistence.entity.DutyEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface DutyRepositoryJpa : JpaRepository<DutyEntity, UUID> {

    @Query("SELECT d FROM DutyEntity d LEFT JOIN FETCH d.theme LEFT JOIN FETCH d.events WHERE d.theme.name = :themeName")
    fun findByThemeName(@Param("themeName") themeName: String): List<DutyEntity>

    @Query("SELECT d FROM DutyEntity d LEFT JOIN FETCH d.theme LEFT JOIN FETCH d.events WHERE d.dutyType = :dutyType")
    fun findByDutyType(@Param("dutyType") dutyType: DutyTypeEnum): List<DutyEntity>

    @Query("SELECT d FROM DutyEntity d LEFT JOIN FETCH d.theme LEFT JOIN FETCH d.events WHERE d.theme.name = :themeName AND d.dutyType = :dutyType")
    fun findByThemeNameAndDutyType(
        @Param("themeName") themeName: String,
        @Param("dutyType") dutyType: DutyTypeEnum
    ): List<DutyEntity>

    @Query("SELECT d FROM DutyEntity d LEFT JOIN FETCH d.theme LEFT JOIN FETCH d.events")
    override fun findAll(): List<DutyEntity>

    @Query("SELECT d FROM DutyEntity d LEFT JOIN FETCH d.theme LEFT JOIN FETCH d.events WHERE d.id = :id")
    fun findByIdCustom(@Param("id") id: UUID): Optional<DutyEntity>

    @Query("SELECT d FROM DutyEntity d LEFT JOIN FETCH d.theme LEFT JOIN FETCH d.events WHERE d.dutyType = :dutyType AND MONTH(d.date) = :month AND YEAR(d.date) = :year ORDER BY d.date DESC")
    fun findByDutyTypeAndMonthAndYear(
        @Param("dutyType") dutyType: DutyTypeEnum,
        @Param("month") month: Int,
        @Param("year") year: Int
    ): List<DutyEntity>

    @Query("SELECT d FROM DutyEntity d LEFT JOIN FETCH d.theme LEFT JOIN FETCH d.events WHERE d.dutyType = :dutyType ORDER BY d.date DESC")
    fun findLatestByDutyType(@Param("dutyType") dutyType: DutyTypeEnum): List<DutyEntity>
}
