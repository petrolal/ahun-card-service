package com.petrolal.ahun.ahundutyservice.application.ports

import com.petrolal.ahun.ahundutyservice.domain.Duty

/**
 * Outbound port interface for managing [Duty] persistence.
 * Implemented by database adapters to communicate with the database.
 */
interface DutyRepositoryPort {

    /**
     * Retrieves all duties from the database.
     *
     * @return List of all [Duty]s.
     */
    fun findAll(): List<Duty>

    /**
     * Retrieves all duties associated with a theme name.
     *
     * @param themeName The name of the theme.
     * @return List of matching [Duty]s.
     */
    fun findByThemeName(themeName: String): List<Duty>

    /**
     * Persists a new duty assignment.
     *
     * @param duty The [Duty] domain object to save.
     * @return The persisted [Duty] domain object.
     */
    fun create(duty: Duty): Duty
}
