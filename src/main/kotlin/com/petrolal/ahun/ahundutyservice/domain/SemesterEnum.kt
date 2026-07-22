package com.petrolal.ahun.ahundutyservice.domain

import java.time.LocalDate

enum class SemesterEnum(var value: String) {
    FIRST_SEMESTER("first_semester"),
    SECOND_SEMESTER("second_semester"),
    VACATION("vacation");

    companion object {
        /**
         * Automatically calculates the [SemesterEnum] based on the provided date.
         * Months 1 to 6 (January to June) -> FIRST_SEMESTER
         * Months 7 to 12 (July to December) -> SECOND_SEMESTER
         */
        fun from(date: LocalDate): SemesterEnum {
            return if (date.monthValue in 1..6) {
                FIRST_SEMESTER
            } else {
                SECOND_SEMESTER
            }
        }
    }
}