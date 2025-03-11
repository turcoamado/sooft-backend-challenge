package com.soofttechnology.challenge.infrastructure.persistence.repository

import com.soofttechnology.challenge.infrastructure.persistence.entity.CompanyEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CompanyRepository : JpaRepository<CompanyEntity, Long> {
    fun save(userEntity: CompanyEntity) : CompanyEntity
    fun existsByCuit(cuit: String): Boolean
    fun existsByCompanyName(companyName: String): Boolean
}