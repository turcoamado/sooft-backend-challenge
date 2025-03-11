package com.soofttechnology.challenge.application.service

import com.soofttechnology.challenge.infrastructure.adapter.dto.CompanyResponse
import com.soofttechnology.challenge.infrastructure.adapter.dto.RegisterCompanyRequest

interface CompanyService {
    fun register(request: RegisterCompanyRequest): CompanyResponse
    fun getCompaniesWithTransactionsInLastMonth(): List<CompanyResponse>
    fun getCompaniesRegisteredInLastMonth(): List<CompanyResponse>
}