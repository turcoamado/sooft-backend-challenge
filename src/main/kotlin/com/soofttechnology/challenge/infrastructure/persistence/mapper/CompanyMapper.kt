package com.soofttechnology.challenge.infrastructure.persistence.mapper

import com.soofttechnology.challenge.domain.model.Company
import com.soofttechnology.challenge.infrastructure.adapter.dto.CompanyResponse
import com.soofttechnology.challenge.infrastructure.adapter.dto.RegisterCompanyRequest
import com.soofttechnology.challenge.infrastructure.persistence.entity.CompanyEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface CompanyMapper {
    fun requestToDomain(request: RegisterCompanyRequest): Company
    fun domainToEntity(company: Company): CompanyEntity
    fun entityToDomain(companyEntity: CompanyEntity): Company
    fun domainToResponse(company: Company): CompanyResponse
    fun entityListToDomainList(companyEntityList: List<CompanyEntity>): List<Company>
    fun domainListToResponseList(companyList: List<Company>): List<CompanyResponse>
}
