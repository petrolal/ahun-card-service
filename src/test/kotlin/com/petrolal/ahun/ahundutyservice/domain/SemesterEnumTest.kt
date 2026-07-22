package com.petrolal.ahun.ahundutyservice.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class SemesterEnumTest {

    @Test
    fun `should return FIRST_SEMESTER for dates in months 1 to 6`() {
        val dateJan = LocalDate.of(2026, 1, 15)
        val dateMay = LocalDate.of(2026, 5, 20)
        val dateJun = LocalDate.of(2026, 6, 30)

        assertEquals(SemesterEnum.FIRST_SEMESTER, SemesterEnum.from(dateJan))
        assertEquals(SemesterEnum.FIRST_SEMESTER, SemesterEnum.from(dateMay))
        assertEquals(SemesterEnum.FIRST_SEMESTER, SemesterEnum.from(dateJun))
    }

    @Test
    fun `should return SECOND_SEMESTER for dates in months 7 to 12`() {
        val dateJul = LocalDate.of(2026, 7, 1)
        val dateOct = LocalDate.of(2026, 10, 15)
        val dateDec = LocalDate.of(2026, 12, 31)

        assertEquals(SemesterEnum.SECOND_SEMESTER, SemesterEnum.from(dateJul))
        assertEquals(SemesterEnum.SECOND_SEMESTER, SemesterEnum.from(dateOct))
        assertEquals(SemesterEnum.SECOND_SEMESTER, SemesterEnum.from(dateDec))
    }
}
