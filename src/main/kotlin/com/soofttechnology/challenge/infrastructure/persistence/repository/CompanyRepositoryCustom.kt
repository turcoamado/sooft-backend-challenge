package com.soofttechnology.challenge.infrastructure.persistence.repository

import com.soofttechnology.challenge.infrastructure.persistence.entity.CompanyEntity
import org.springframework.stereotype.Repository

@Repository
interface CompanyRepositoryCustom {
    fun getCompaniesWithTransactionsInLastMonth(): List<CompanyEntity>
    fun getCompaniesRegisteredInLastMonth(): List<CompanyEntity>
}