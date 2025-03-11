package com.soofttechnology.challenge.domain.model

import java.time.LocalDateTime

data class Company(
    val id: Long,
    val cuit: String? = null,
    val companyName: String? = null,
    val registrationDate: LocalDateTime? = null
)
