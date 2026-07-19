package com.petrolal.ahun.ahundutyservice.infrastructure.persistence.repository

import com.petrolal.ahun.ahundutyservice.infrastructure.persistence.entity.DutyEventEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface DutyEventRepositoryJpa : JpaRepository<DutyEventEntity, UUID>