package com.petrolal.ahun.ahundutyservice.application.usecases

import com.petrolal.ahun.ahundutyservice.application.ports.CardRenderPort
import com.petrolal.ahun.ahundutyservice.application.ports.CardUsecasePort
import com.petrolal.ahun.ahundutyservice.application.ports.DutyRepositoryPort
import com.petrolal.ahun.ahundutyservice.domain.Duty
import com.petrolal.ahun.ahundutyservice.domain.DutyTypeEnum
import com.petrolal.ahun.ahundutyservice.domain.exception.ResourceNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.UUID

/**
 * Application service orchestrating Card generation business logic.
 * Implements [CardUsecasePort] following Hexagonal Architecture.
 */
@Service
@Transactional(readOnly = true)
class CardUsecase(
    private val dutyRepository: DutyRepositoryPort,
    private val cardRenderPort: CardRenderPort
) : CardUsecasePort {

    override fun getPreview(
        dutyId: UUID?,
        overrideTitle: String?,
        overrideSubtitle: String?
    ): String {
        val (title, subtitle) = resolveTitleAndSubtitle(dutyId, overrideTitle, overrideSubtitle)
        val variables = mapOf(
            "eventTitle" to title,
            "eventSubtitle" to subtitle
        )
        return cardRenderPort.renderHtml("generic_2_fields_template", variables)
    }

    override fun renderCardPng(
        dutyId: UUID?,
        overrideTitle: String?,
        overrideSubtitle: String?
    ): ByteArray {
        val (title, subtitle) = resolveTitleAndSubtitle(dutyId, overrideTitle, overrideSubtitle)
        val variables = mapOf(
            "eventTitle" to title,
            "eventSubtitle" to subtitle
        )
        return cardRenderPort.renderPng("generic_2_fields_template", variables)
    }

    private fun resolveTitleAndSubtitle(
        dutyId: UUID?,
        overrideTitle: String?,
        overrideSubtitle: String?
    ): Pair<String, String> {
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

        return Pair(title, subtitle)
    }

    private fun formatSubtitle(duty: Duty): String {
        val date = duty.date
        val dayOfWeek = when (date.dayOfWeek) {
            DayOfWeek.MONDAY -> "SEGUNDA-FEIRA"
            DayOfWeek.TUESDAY -> "TERÇA-FEIRA"
            DayOfWeek.WEDNESDAY -> "QUARTA-FEIRA"
            DayOfWeek.THURSDAY -> "QUINTA-FEIRA"
            DayOfWeek.FRIDAY -> "SEXTA-FEIRA"
            DayOfWeek.SATURDAY -> "SÁBADO"
            DayOfWeek.SUNDAY -> "DOMINGO"
        }

        val month = when (date.monthValue) {
            1 -> "JANEIRO"
            2 -> "FEVEREIRO"
            3 -> "MARÇO"
            4 -> "ABRIL"
            5 -> "MAIO"
            6 -> "JUNHO"
            7 -> "JULHO"
            8 -> "AGOSTO"
            9 -> "SETEMBRO"
            10 -> "OUTUBRO"
            11 -> "NOVEMBRO"
            12 -> "DEZEMBRO"
            else -> ""
        }

        val dateStr = "$dayOfWeek | ${date.dayOfMonth} DE $month"

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
