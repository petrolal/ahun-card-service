package com.petrolal.ahun.ahuncardservice.domain.ports

import com.petrolal.ahun.ahuncardservice.domain.models.GiraEvent

interface GiraEventRepositoryPort {

    fun findAll(): List<GiraEvent>

}