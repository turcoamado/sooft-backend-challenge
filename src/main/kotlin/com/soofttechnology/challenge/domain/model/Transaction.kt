package com.soofttechnology.challenge.domain.model

import java.time.LocalDateTime

data class Transaction(
    private val id: Long,
    private val company: Company? = null,
    private val amount: Double? = null,
    private val debitAccount: String? = null,
    private val creditAccount: String? = null,
    private val date: LocalDateTime? = null
)
