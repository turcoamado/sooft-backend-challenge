package com.soofttechnology.challenge.infrastructure.adapter.dto

import java.time.LocalDateTime

data class CompanyResponse(
    val cuit: String,
    val companyName: String,
    val registrationDate: LocalDateTime
)
