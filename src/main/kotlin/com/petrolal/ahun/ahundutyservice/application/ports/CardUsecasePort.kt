package com.petrolal.ahun.ahundutyservice.application.ports

import java.util.UUID

/**
 * Inbound port interface for Card generation business use cases.
 */
interface CardUsecasePort {

    /**
     * Generates the HTML preview for a card based on a specific duty ID or the actual month's GIRA_ABERTA duty.
     *
     * @param dutyId Optional specific duty UUID to render.
     * @param overrideTitle Optional custom title text overriding the duty theme name.
     * @param overrideSubtitle Optional custom subtitle text overriding the formatted duty date/time.
     * @return Processed HTML content string.
     */
    fun getPreview(
        dutyId: UUID? = null,
        overrideTitle: String? = null,
        overrideSubtitle: String? = null
    ): String

    /**
     * Renders a PNG image byte array for a card based on a specific duty ID or the actual month's GIRA_ABERTA duty.
     *
     * @param dutyId Optional specific duty UUID to render.
     * @param overrideTitle Optional custom title text overriding the duty theme name.
     * @param overrideSubtitle Optional custom subtitle text overriding the formatted duty date/time.
     * @return PNG image byte array.
     */
    fun renderCardPng(
        dutyId: UUID? = null,
        overrideTitle: String? = null,
        overrideSubtitle: String? = null
    ): ByteArray
}