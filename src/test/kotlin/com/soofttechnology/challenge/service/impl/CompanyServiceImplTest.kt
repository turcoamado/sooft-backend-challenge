package com.soofttechnology.challenge.service.impl

import com.soofttechnology.challenge.application.service.CompanyService
import com.soofttechnology.challenge.infrastructure.adapter.dto.CompanyResponse
import com.soofttechnology.challenge.infrastructure.adapter.dto.RegisterCompanyRequest
import com.soofttechnology.challenge.infrastructure.persistence.entity.CompanyEntity
import com.soofttechnology.challenge.infrastructure.persistence.mapper.CompanyMapper
import com.soofttechnology.challenge.infrastructure.persistence.repository.CompanyRepository
import com.soofttechnology.challenge.infrastructure.persistence.repository.CompanyRepositoryCustom
import com.soofttechnology.challenge.infrastructure.service.impl.CompanyServiceImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class CompanyServiceImplTest {

    private lateinit var companyMapper: CompanyMapper
    private lateinit var companyRepository: CompanyRepository
    private lateinit var companyRepositoryCustom: CompanyRepositoryCustom
    private lateinit var companyService: CompanyService

    @BeforeEach
    fun setup() {
        companyMapper = mockk(relaxed = true)
        companyRepository = mockk()
        companyRepositoryCustom = mockk()
        companyService = CompanyServiceImpl(companyMapper, companyRepository, companyRepositoryCustom)
    }

    @Test
    fun `getCompaniesWithTransactionsInLastMonth should return list of companies`() {
        val companyEntities = listOf(mockk<CompanyEntity>())
        val companyResponses = listOf(mockk<CompanyResponse>())

        every { companyRepositoryCustom.getCompaniesWithTransactionsInLastMonth() } returns companyEntities
        every { companyMapper.entityListToDomainList(companyEntities) } returns mockk()
        every { companyMapper.domainListToResponseList(any()) } returns companyResponses

        val result = companyService.getCompaniesWithTransactionsInLastMonth()

        assertEquals(companyResponses, result)
        verify { companyRepositoryCustom.getCompaniesWithTransactionsInLastMonth() }
        verify { companyMapper.domainListToResponseList(any()) }
    }

    @Test
    fun `getCompaniesRegisteredInLastMonth should return list of companies`() {
        val companyEntities = listOf(mockk<CompanyEntity>())
        val companyResponses = listOf(mockk<CompanyResponse>())

        every { companyRepositoryCustom.getCompaniesRegisteredInLastMonth() } returns companyEntities
        every { companyMapper.entityListToDomainList(companyEntities) } returns mockk()
        every { companyMapper.domainListToResponseList(any()) } returns companyResponses

        val result = companyService.getCompaniesRegisteredInLastMonth()

        assertEquals(companyResponses, result)
        verify { companyRepositoryCustom.getCompaniesRegisteredInLastMonth() }
        verify { companyMapper.domainListToResponseList(any()) }
    }

    @Test
    fun `register should throw exception if CUIT format is invalid`() {
        val request = RegisterCompanyRequest("12345678901", "Test Company")

        val exception = assertThrows<ResponseStatusException> {
            companyService.register(request)
        }

        assertEquals(HttpStatus.BAD_REQUEST, exception.statusCode)
        assertEquals("Invalid CUIT. Format is not correct, must be XX-XXXXXXXX-X and start with 20, 23, 24, 27, 30, 33 o 34.", exception.reason)
    }

    @Test
    fun `register should throw exception if verifier digit is not valid`() {
        val request = RegisterCompanyRequest("33-68521479-9", "Test Company")

        val exception = assertThrows<ResponseStatusException> {
            companyService.register(request)
        }

        assertEquals(HttpStatus.BAD_REQUEST, exception.statusCode)
        assertEquals("Invalid CUIT. The verifier digit is not valid.", exception.reason)
    }

    @Test
    fun `register should throw exception if CUIT already exists`() {
        val request = RegisterCompanyRequest("33-68521479-8", "Test Company")

        every { companyRepository.existsByCuit(request.cuit) } returns true

        val exception = assertThrows<ResponseStatusException> {
            companyService.register(request)
        }

        assertEquals(HttpStatus.BAD_REQUEST, exception.statusCode)
        assertEquals("A company with this CUIT already exists.", exception.reason)
    }

    @Test
    fun `register should throw exception if company name already exists`() {
        val request = RegisterCompanyRequest("33-68521479-8", "Test Company")

        every { companyRepository.existsByCuit(request.cuit) } returns false
        every { companyRepository.existsByCompanyName(request.companyName) } returns true

        val exception = assertThrows<ResponseStatusException> {
            companyService.register(request)
        }

        assertEquals(HttpStatus.BAD_REQUEST, exception.statusCode)
        assertEquals("A company with this name already exists.", exception.reason)
    }

    @Test
    fun `register should save and return company response`() {
        val request = RegisterCompanyRequest("33-68521479-8", "Test Company")
        val companyEntity = mockk<CompanyEntity>(relaxed = true)
        val companyResponse = mockk<CompanyResponse>()

        every { companyRepository.existsByCuit(request.cuit) } returns false
        every { companyRepository.existsByCompanyName(request.companyName) } returns false
        every { companyMapper.requestToDomain(request) } returns mockk()
        every { companyMapper.domainToEntity(any()) } returns companyEntity
        every { companyRepository.save(companyEntity) } returns companyEntity
        every { companyMapper.domainToResponse(any()) } returns companyResponse

        val result = companyService.register(request)

        assertEquals(companyResponse, result)
        verify { companyRepository.save(companyEntity) }
    }

}