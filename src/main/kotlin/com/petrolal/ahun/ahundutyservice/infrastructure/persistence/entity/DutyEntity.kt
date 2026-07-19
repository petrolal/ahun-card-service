package com.petrolal.ahun.ahundutyservice.infrastructure.persistence.entity

import com.petrolal.ahun.ahundutyservice.domain.DutyTypeEnum
import com.petrolal.ahun.ahundutyservice.domain.SemesterEnum
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "duties")
class DutyEntity(
    @Id
    var id: UUID,

    @Column(nullable = false)
    var date: LocalDate,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id")
    var theme: ThemeEntity,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var dutyType: DutyTypeEnum,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var period: SemesterEnum,

    @Column(nullable = true)
    var description: String?,

    @Column(nullable = false)
    var year: Int,

    @ManyToMany
    @JoinTable(
        name = "duties_events",
        joinColumns = [JoinColumn(name = "duty_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "event_id", referencedColumnName = "id")]
    )
    var events: MutableList<DutyEventEntity>,

    @Column(nullable = false)
    var createdAt: LocalDateTime,

    @Column(nullable = true)
    var updatedAt: LocalDateTime?,

    )