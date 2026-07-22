package com.petrolal.ahun.ahundutyservice.application.usecases

import com.petrolal.ahun.ahundutyservice.application.ports.CardRenderPort
import com.petrolal.ahun.ahundutyservice.application.ports.DutyRepositoryPort
import com.petrolal.ahun.ahundutyservice.domain.*
import com.petrolal.ahun.ahundutyservice.domain.exception.ResourceNotFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

class CardUsecaseTest {

    private lateinit var dutyRepository: DutyRepositoryPort
    private lateinit var cardRenderPort: CardRenderPort
    private lateinit var cardUsecase: CardUsecase

    @Suppress("UNCHECKED_CAST")
    private fun <T> anyObj(): T {
        any<T>()
        return null as T
    }

    @BeforeEach
    fun setUp() {
        dutyRepository = mock(DutyRepositoryPort::class.java)
        cardRenderPort = mock(CardRenderPort::class.java)
        cardUsecase = CardUsecase(dutyRepository, cardRenderPort)
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

        `when`(dutyRepository.findCurrentMonthDutyByType(anyObj(), anyInt(), anyInt()))
            .thenReturn(duty)
        `when`(cardRenderPort.renderHtml(anyString(), anyObj()))
            .thenReturn("<html>GIRA DE EXU E CURA</html>")

        val html = cardUsecase.getPreview()

        assertNotNull(html)
        assertTrue(html.contains("GIRA DE EXU E CURA"))
    }

    @Test
    fun `should throw ResourceNotFoundException if no GIRA_ABERTA duty is found`() {
        `when`(dutyRepository.findCurrentMonthDutyByType(anyObj(), anyInt(), anyInt()))
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

        `when`(dutyRepository.findById(dutyId)).thenReturn(duty)
        `when`(cardRenderPort.renderPng(anyString(), anyObj()))
            .thenReturn(byteArrayOf(1, 2, 3))

        val bytes = cardUsecase.renderCardPng(dutyId = dutyId)

        assertNotNull(bytes)
        assertEquals(3, bytes.size)
        verify(dutyRepository).findById(dutyId)
    }
}
