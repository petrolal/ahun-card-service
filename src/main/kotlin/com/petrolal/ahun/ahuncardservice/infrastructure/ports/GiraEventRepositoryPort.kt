package com.petrolal.ahun.ahuncardservice.infrastructure.ports

import com.petrolal.ahun.ahuncardservice.domain.GiraEvent

interface GiraEventRepositoryPort {

    fun findAll(): List<GiraEvent>

}