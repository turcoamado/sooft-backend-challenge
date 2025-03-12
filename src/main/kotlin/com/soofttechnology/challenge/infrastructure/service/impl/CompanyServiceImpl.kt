package com.soofttechnology.challenge.infrastructure.service.impl

import com.soofttechnology.challenge.application.service.CompanyService
import com.soofttechnology.challenge.domain.model.CuitValidator
import com.soofttechnology.challenge.infrastructure.adapter.dto.CompanyResponse
import com.soofttechnology.challenge.infrastructure.adapter.dto.RegisterCompanyRequest
import com.soofttechnology.challenge.infrastructure.persistence.mapper.CompanyMapper
import com.soofttechnology.challenge.infrastructure.persistence.repository.CompanyRepository
import com.soofttechnology.challenge.infrastructure.persistence.repository.CompanyRepositoryCustom
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Service
class CompanyServiceImpl(
    private val companyMapper: CompanyMapper,
    private val companyRepository: CompanyRepository,
    private val companyRepositoryCustom: CompanyRepositoryCustom
) : CompanyService {

    override fun getCompaniesWithTransactionsInLastMonth(): List<CompanyResponse> {
        val companyEntityList = companyRepositoryCustom.getCompaniesWithTransactionsInLastMonth()
        return companyMapper.domainListToResponseList(companyMapper.entityListToDomainList(companyEntityList))  // Entity -> Domain -> Response
    }

    override fun getCompaniesRegisteredInLastMonth(): List<CompanyResponse> {
        val companyEntityList = companyRepositoryCustom.getCompaniesRegisteredInLastMonth()
        return companyMapper.domainListToResponseList(companyMapper.entityListToDomainList(companyEntityList)) // Entity -> Domain -> Response
    }

    override fun register(request: RegisterCompanyRequest): CompanyResponse {
        validateCompanyData(request)

        val companyEntity = companyMapper.domainToEntity(companyMapper.requestToDomain(request)).apply { // Convert to DTO -> Domain -> Entity
            registrationDate = LocalDateTime.now()
        }

        val savedCompany = companyRepository.save(companyEntity)
        return companyMapper.domainToResponse(companyMapper.entityToDomain(savedCompany)) // Entity -> Domain -> Response
    }

    private fun validateCompanyData(request: RegisterCompanyRequest) {
        CuitValidator.validate(request.cuit)?.let { throw ResponseStatusException(HttpStatus.BAD_REQUEST, it) }

        when {
            companyRepository.existsByCuit(request.cuit) -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, "A company with this CUIT already exists.")
            companyRepository.existsByCompanyName(request.companyName) -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, "A company with this name already exists.")
        }
    }
}