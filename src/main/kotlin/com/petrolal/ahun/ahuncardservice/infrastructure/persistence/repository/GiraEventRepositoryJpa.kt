package com.petrolal.ahun.ahuncardservice.infrastructure.persistence.repository

import com.petrolal.ahun.ahuncardservice.infrastructure.persistence.entity.GiraEventEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface GiraEventRepositoryJpa : JpaRepository<GiraEventEntity, UUID>