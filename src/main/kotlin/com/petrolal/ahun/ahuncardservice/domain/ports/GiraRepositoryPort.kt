package com.petrolal.ahun.ahuncardservice.domain.ports

import com.petrolal.ahun.ahuncardservice.domain.models.Gira

interface GiraRepositoryPort {

    fun findByThemeName(themeName: String): List<Gira>

}
