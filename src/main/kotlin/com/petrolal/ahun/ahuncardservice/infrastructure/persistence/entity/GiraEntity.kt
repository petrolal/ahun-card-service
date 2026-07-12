package com.petrolal.ahun.ahuncardservice.infrastructure.persistence.entity

import com.petrolal.ahun.ahuncardservice.domain.models.SemesterEnum
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "giras")
class GiraEntity(
    @Id
    var id: UUID,

    @Column(nullable = false)
    var date: LocalDate,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id")
    var theme: ThemeEntity,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var period: SemesterEnum,

    @Column(nullable = true)
    var description: String?,

    @Column(nullable = false)
    var year: Int,

    @ManyToMany
    @JoinTable(
        name = "giras_events",
        joinColumns = [JoinColumn(name = "gira_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "event_id", referencedColumnName = "id")]
    )
    var events: MutableList<GiraEventEntity>,

    @Column(nullable = false)
    var createdAt: LocalDateTime,

    @Column(nullable = true)
    var updatedAt: LocalDateTime?,

    )