package com.petrolal.ahun.ahundutyservice.application.usecases

import com.petrolal.ahun.ahundutyservice.application.ports.CardRenderPort
import com.petrolal.ahun.ahundutyservice.application.ports.CardUsecasePort
import com.petrolal.ahun.ahundutyservice.application.ports.DutyRepositoryPort
import com.petrolal.ahun.ahundutyservice.application.ports.TemplateRepositoryPort
import com.petrolal.ahun.ahundutyservice.domain.Duty
import com.petrolal.ahun.ahundutyservice.domain.DutyTypeEnum
import com.petrolal.ahun.ahundutyservice.domain.Theme
import com.petrolal.ahun.ahundutyservice.domain.exception.ResourceNotFoundException
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.text.Normalizer
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Base64
import java.util.Locale
import java.util.UUID

/**
 * Application service orchestrating Card generation business logic.
 * Implements [CardUsecasePort] following Hexagonal Architecture.
 */
@Service
@Transactional(readOnly = true)
class CardUsecase(
    private val dutyRepository: DutyRepositoryPort,
    private val cardRenderPort: CardRenderPort,
    private val templateRepository: TemplateRepositoryPort
) : CardUsecasePort {

    override fun getPreview(
        dutyId: UUID?,
        overrideTitle: String?,
        overrideSubtitle: String?
    ): String {
        val cardData = resolveCardData(dutyId, overrideTitle, overrideSubtitle)
        val variables = mapOf(
            "dutyId" to (dutyId?.toString() ?: ""),
            "eventTitle" to cardData.title,
            "eventSubtitle" to cardData.subtitle,
            "bgImageName" to cardData.bgImageName,
            "bgImageDataUri" to cardData.bgImageDataUri
        )
        return cardRenderPort.renderHtml("preview_card_template", variables)
    }

    override fun renderCardPng(
        dutyId: UUID?,
        overrideTitle: String?,
        overrideSubtitle: String?
    ): ByteArray {
        val cardData = resolveCardData(dutyId, overrideTitle, overrideSubtitle)
        val variables = mapOf(
            "dutyId" to (dutyId?.toString() ?: ""),
            "eventTitle" to cardData.title,
            "eventSubtitle" to cardData.subtitle,
            "bgImageName" to cardData.bgImageName,
            "bgImageDataUri" to cardData.bgImageDataUri
        )
        return cardRenderPort.renderPng("generic_2_fields_template", variables)
    }

    private data class CardData(
        val title: String,
        val subtitle: String,
        val bgImageName: String,
        val bgImageDataUri: String
    )

    private fun resolveCardData(
        dutyId: UUID?,
        overrideTitle: String?,
        overrideSubtitle: String?
    ): CardData {
        val duty = if (dutyId != null) {
            dutyRepository.findById(dutyId)
                ?: throw ResourceNotFoundException("Duty with id $dutyId not found")
        } else {
            val now = LocalDate.now()
            dutyRepository.findCurrentMonthDutyByType(
                dutyType = DutyTypeEnum.OPENED_GIRA,
                year = now.year,
                month = now.monthValue
            ) ?: throw ResourceNotFoundException("No GIRA_ABERTA duty found in database for actual month or latest records")
        }

        val title = overrideTitle?.takeIf { it.isNotBlank() }
            ?: duty.theme.name.uppercase()

        val subtitle = overrideSubtitle?.takeIf { it.isNotBlank() }
            ?: formatSubtitle(duty)

        val bgImageName = resolveBgImageForTheme(duty.theme)
        val bgImageDataUri = loadBase64Image(bgImageName)

        return CardData(title, subtitle, bgImageName, bgImageDataUri)
    }

    private fun resolveBgImageForTheme(theme: Theme): String {
        // 1. Query database for templates associated with this specific theme
        val dbTemplates = templateRepository.findByThemeId(theme.id)
        if (dbTemplates.isNotEmpty()) {
            // Pick a random template if multiple templates exist for this theme
            return dbTemplates.random().imagePath
        }

        // 2. Fallback matching based on normalized theme name
        val normalizedName = normalizeKey(theme.name)
        val allStaticImages = listOf(
            "atendimento_de_cura_2.png",
            "feijoada_dos_vovos.png",
            "feijoada_dos_vovos_2.png",
            "festa_de_7_saias.png",
            "festa_de_eres_e_pretos_velhos.png",
            "gira_de_cura_caboclos_e_boiadeiros.png",
            "gira_de_encerramento_exu.png",
            "gira_de_eres.png",
            "gira_de_eres_e_cura.png",
            "gira_de_exu_e_cura.png",
            "gira_de_exu_e_cura_2.png",
            "gira_de_exu_e_cura_3.png",
            "gira_de_pretos_velhos_e_cura_2.png"
        )

        val matchingCandidates = allStaticImages.filter { img ->
            val imgBase = img.removeSuffix(".png").replace(Regex("_\\d+$"), "")
            imgBase == normalizedName || normalizedName.contains(imgBase) || imgBase.contains(normalizedName)
        }

        if (matchingCandidates.isNotEmpty()) {
            return matchingCandidates.random()
        }

        return "gira_de_exu_e_cura_2.png"
    }

    private fun normalizeKey(input: String): String {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
            .replace(Regex("\\p{InCombiningDiacriticalMarks}+"), "")
            .lowercase()
            .replace(Regex("[^a-z0-9]+"), "_")
            .trim('_')
    }

    private fun loadBase64Image(imageName: String): String {
        return try {
            val resource = ClassPathResource("static/images/$imageName")
            if (resource.exists()) {
                val bytes = resource.inputStream.readAllBytes()
                val base64 = Base64.getEncoder().encodeToString(bytes)
                "data:image/png;base64,$base64"
            } else {
                val fallback = ClassPathResource("static/images/gira_de_exu_e_cura_2.png")
                if (fallback.exists()) {
                    val bytes = fallback.inputStream.readAllBytes()
                    val base64 = Base64.getEncoder().encodeToString(bytes)
                    "data:image/png;base64,$base64"
                } else ""
            }
        } catch (_: Exception) {
            ""
        }
    }

    private fun formatSubtitle(duty: Duty): String {
        val dateFormatter = DateTimeFormatter.ofPattern("EEEE | d 'DE' MMMM", Locale.of("pt", "BR"))
        val dateStr = duty.date.format(dateFormatter).uppercase()

        val visibleEvent = duty.events.filter { it.visibleInCard }.minByOrNull { it.startedAt }
            ?: duty.events.minByOrNull { it.startedAt }

        return if (visibleEvent != null) {
            val hour = visibleEvent.startedAt.hour
            val minute = visibleEvent.startedAt.minute
            val timeStr = if (minute == 0) "${hour}H" else "${hour}H${minute.toString().padStart(2, '0')}"
            "$dateStr - $timeStr"
        } else {
            dateStr
        }
    }
}
