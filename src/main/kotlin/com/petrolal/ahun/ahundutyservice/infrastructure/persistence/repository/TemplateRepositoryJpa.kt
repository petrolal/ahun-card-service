package com.petrolal.ahun.ahundutyservice.infrastructure.persistence.repository

import com.petrolal.ahun.ahundutyservice.infrastructure.persistence.entity.TemplateEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface TemplateRepositoryJpa : JpaRepository<TemplateEntity, UUID> {

    @Query("SELECT t FROM TemplateEntity t WHERE t.theme.id = :themeId")
    fun findByThemeId(@Param("themeId") themeId: UUID): List<TemplateEntity>
}
