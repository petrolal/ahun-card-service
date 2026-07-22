package com.petrolal.ahun.ahundutyservice.infrastructure.adapters.inbound.rest

import com.petrolal.ahun.ahundutyservice.application.ports.CardUsecasePort
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

/**
 * Inbound REST controller adapter for managing and generating card image previews and PNGs.
 * Follows Hexagonal Architecture by delegating domain logic to [CardUsecasePort].
 */
@Tag(name = "Cards", description = "Endpoint to organize and render cards")
@RestController
@RequestMapping("cards")
class CardResource(
    private val cardUsecase: CardUsecasePort
) {

    /**
     * Endpoint to preview the card HTML using query parameters.
     * Fetches card data for the given duty ID or defaults to the actual month's GIRA_ABERTA duty.
     *
     * @param dutyId Optional UUID of a specific duty.
     * @param title Optional title override.
     * @param subtitle Optional subtitle override.
     * @return Processed Thymeleaf HTML string.
     */
    @GetMapping("preview")
    fun preview(
        @RequestParam(name = "dutyId", required = false) dutyId: UUID?,
        @RequestParam(name = "title", required = false) title: String?,
        @RequestParam(name = "subtitle", required = false) subtitle: String?
    ): String {
        return cardUsecase.getPreview(dutyId = dutyId, overrideTitle = title, overrideSubtitle = subtitle)
    }

    /**
     * Endpoint to preview the card HTML for a specific duty ID path variable.
     *
     * @param dutyId UUID of the specific duty.
     * @param title Optional title override.
     * @param subtitle Optional subtitle override.
     * @return Processed Thymeleaf HTML string.
     */
    @GetMapping("preview/{dutyId}")
    fun previewForDuty(
        @PathVariable("dutyId") dutyId: UUID,
        @RequestParam(name = "title", required = false) title: String?,
        @RequestParam(name = "subtitle", required = false) subtitle: String?
    ): String {
        return cardUsecase.getPreview(dutyId = dutyId, overrideTitle = title, overrideSubtitle = subtitle)
    }

    /**
     * Endpoint to render and export the card PNG image byte array using query parameters.
     * Fetches card data for the given duty ID or defaults to the actual month's GIRA_ABERTA duty.
     *
     * @param dutyId Optional UUID of a specific duty.
     * @param title Optional title override.
     * @param subtitle Optional subtitle override.
     * @return PNG image response byte array.
     */
    @GetMapping("render", produces = [MediaType.IMAGE_PNG_VALUE])
    fun generateCard(
        @RequestParam(name = "dutyId", required = false) dutyId: UUID?,
        @RequestParam(name = "title", required = false) title: String?,
        @RequestParam(name = "subtitle", required = false) subtitle: String?
    ): ResponseEntity<ByteArray> {
        val pngBytes = cardUsecase.renderCardPng(dutyId = dutyId, overrideTitle = title, overrideSubtitle = subtitle)
        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_PNG)
            .body(pngBytes)
    }

    /**
     * Endpoint to render and export the card PNG image byte array for a specific duty ID path variable.
     *
     * @param dutyId UUID of the specific duty.
     * @param title Optional title override.
     * @param subtitle Optional subtitle override.
     * @return PNG image response byte array.
     */
    @GetMapping("render/{dutyId}", produces = [MediaType.IMAGE_PNG_VALUE])
    fun generateCardForDuty(
        @PathVariable("dutyId") dutyId: UUID,
        @RequestParam(name = "title", required = false) title: String?,
        @RequestParam(name = "subtitle", required = false) subtitle: String?
    ): ResponseEntity<ByteArray> {
        val pngBytes = cardUsecase.renderCardPng(dutyId = dutyId, overrideTitle = title, overrideSubtitle = subtitle)
        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_PNG)
            .body(pngBytes)
    }
}