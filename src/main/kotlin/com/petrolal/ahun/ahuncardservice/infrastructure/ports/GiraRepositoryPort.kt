package com.petrolal.ahun.ahuncardservice.infrastructure.ports

import com.petrolal.ahun.ahuncardservice.domain.Gira

interface GiraRepositoryPort {

    fun findByThemeName(themeName: String): List<Gira>

}
