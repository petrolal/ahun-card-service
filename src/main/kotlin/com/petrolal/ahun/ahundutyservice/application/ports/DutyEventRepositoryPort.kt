package com.petrolal.ahun.ahundutyservice.application.ports

import com.petrolal.ahun.ahundutyservice.domain.DutyEvent
import java.util.UUID

/**
 * Outbound port interface for managing [DutyEvent] persistence.
 * Implemented by database adapters to communicate with the database.
 */
interface DutyEventRepositoryPort {

    /**
     * Retrieves all duty events from the database.
     *
     * @return List of all [DutyEvent]s.
     */
    fun findAll(): List<DutyEvent>

    /**
     * Persists multiple new duty events.
     *
     * @param events The list of [DutyEvent]s to save.
     * @return The persisted list of [DutyEvent]s.
     */
    fun create(events: List<DutyEvent>): List<DutyEvent>

    /**
     * Retrieves multiple duty events matching the provided unique identifiers.
     *
     * @param ids List of duty event UUIDs.
     * @return List of matching [DutyEvent]s.
     */
    fun findAllById(ids: List<UUID>): List<DutyEvent>
}
