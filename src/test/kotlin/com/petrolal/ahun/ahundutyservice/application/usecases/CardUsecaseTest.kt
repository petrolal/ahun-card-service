package com.petrolal.ahun.ahundutyservice.application.usecases

import com.petrolal.ahun.ahundutyservice.application.ports.CardRenderPort
import com.petrolal.ahun.ahundutyservice.application.ports.DutyRepositoryPort
import com.petrolal.ahun.ahundutyservice.application.ports.TemplateRepositoryPort
import com.petrolal.ahun.ahundutyservice.domain.*
import com.petrolal.ahun.ahundutyservice.domain.exception.ResourceNotFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

class CardUsecaseTest {

    private lateinit var dutyRepository: DutyRepositoryPort
    private lateinit var cardRenderPort: CardRenderPort
    private lateinit var templateRepository: TemplateRepositoryPort
    private lateinit var cardUsecase: CardUsecase

    @BeforeEach
    fun setUp() {
        dutyRepository = mock()
        cardRenderPort = mock()
        templateRepository = mock()
        cardUsecase = CardUsecase(dutyRepository, cardRenderPort, templateRepository)
    }

    @Test
    fun `should generate preview for actual month GIRA_ABERTA duty when no dutyId is provided`() {
        val theme = Theme(UUID.randomUUID(), "Gira de Exu e Cura", null, LocalDateTime.now())
        val event = DutyEvent(UUID.randomUUID(), "Início", LocalTime.of(18, 0), null, true, LocalDateTime.now())
        val duty = Duty(
            id = UUID.randomUUID(),
            theme = theme,
            dutyType = DutyTypeEnum.OPENED_GIRA,
            date = LocalDate.of(2026, 5, 20),
            period = SemesterEnum.FIRST_SEMESTER,
            year = 2026,
            events = mutableSetOf(event),
            createdAt = LocalDateTime.now()
        )

        whenever(dutyRepository.findCurrentMonthDutyByType(eq(DutyTypeEnum.OPENED_GIRA), any(), any()))
            .thenReturn(duty)
        whenever(templateRepository.findByThemeId(theme.id))
            .thenReturn(listOf(Template(UUID.randomUUID(), "Template 1", "gira_de_exu_e_cura.png", theme)))
        whenever(cardRenderPort.renderHtml(any(), any()))
            .thenReturn("<html>GIRA DE EXU E CURA</html>")

        val html = cardUsecase.getPreview()

        assertNotNull(html)
        assertTrue(html.contains("GIRA DE EXU E CURA"))
    }

    @Test
    fun `should throw ResourceNotFoundException if no GIRA_ABERTA duty is found`() {
        whenever(dutyRepository.findCurrentMonthDutyByType(eq(DutyTypeEnum.OPENED_GIRA), any(), any()))
            .thenReturn(null)

        assertThrows(ResourceNotFoundException::class.java) {
            cardUsecase.getPreview()
        }
    }

    @Test
    fun `should generate PNG card for specific dutyId`() {
        val dutyId = UUID.randomUUID()
        val theme = Theme(UUID.randomUUID(), "Gira de Caboclo", null, LocalDateTime.now())
        val duty = Duty(
            id = dutyId,
            theme = theme,
            dutyType = DutyTypeEnum.OPENED_GIRA,
            date = LocalDate.of(2026, 6, 15),
            period = SemesterEnum.FIRST_SEMESTER,
            year = 2026,
            events = mutableSetOf(),
            createdAt = LocalDateTime.now()
        )

        whenever(dutyRepository.findById(dutyId)).thenReturn(duty)
        whenever(templateRepository.findByThemeId(theme.id)).thenReturn(emptyList())
        whenever(cardRenderPort.renderPng(any(), any()))
            .thenReturn(byteArrayOf(1, 2, 3))

        val bytes = cardUsecase.renderCardPng(dutyId = dutyId)

        assertNotNull(bytes)
        assertEquals(3, bytes.size)
        verify(dutyRepository).findById(dutyId)
    }

    @Test
    fun `should pick a random template among theme templates when multiple templates exist for theme`() {
        val dutyId = UUID.randomUUID()
        val theme = Theme(UUID.randomUUID(), "Feijoada dos Vovôs", null, LocalDateTime.now())
        val duty = Duty(
            id = dutyId,
            theme = theme,
            dutyType = DutyTypeEnum.OPENED_GIRA,
            date = LocalDate.of(2026, 5, 20),
            period = SemesterEnum.FIRST_SEMESTER,
            year = 2026,
            events = mutableSetOf(),
            createdAt = LocalDateTime.now()
        )

        val t1 = Template(UUID.randomUUID(), "Template 1", "feijoada_dos_vovos.png", theme)
        val t2 = Template(UUID.randomUUID(), "Template 2", "feijoada_dos_vovos_2.png", theme)

        whenever(dutyRepository.findById(dutyId)).thenReturn(duty)
        whenever(templateRepository.findByThemeId(theme.id)).thenReturn(listOf(t1, t2))
        whenever(cardRenderPort.renderPng(any(), any())).thenReturn(byteArrayOf(5, 6, 7))

        val bytes = cardUsecase.renderCardPng(dutyId = dutyId)

        assertNotNull(bytes)
        verify(templateRepository).findByThemeId(theme.id)
    }
}
