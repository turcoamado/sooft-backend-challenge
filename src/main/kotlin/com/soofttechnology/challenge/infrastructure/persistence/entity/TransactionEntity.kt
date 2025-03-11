package com.soofttechnology.challenge.infrastructure.persistence.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "transactions")
data class TransactionEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", nullable = false)
    var company: CompanyEntity,

    @Column(nullable = false)
    var amount: Double,

    @Column(nullable = false)
    var debitAccount: String,

    @Column(nullable = false)
    var creditAccount: String,

    @Column(nullable = false)
    var date: LocalDateTime
)
