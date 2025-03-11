package com.soofttechnology.challenge.infrastructure.adapter.controller

import com.soofttechnology.challenge.application.service.CompanyService
import com.soofttechnology.challenge.infrastructure.adapter.dto.CompanyResponse
import com.soofttechnology.challenge.infrastructure.adapter.dto.RegisterCompanyRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/companies")
class CompanyController(
    private val companyService: CompanyService
) {

    @GetMapping("/transactions-last-month")
    fun getCompaniesWithTransactionsInLastMonth(): ResponseEntity<List<CompanyResponse>> {
        val companies = companyService.getCompaniesWithTransactionsInLastMonth()
        return ResponseEntity.ok(companies)
    }

    @GetMapping("/registered-last-month")
    fun getCompaniesRegisteredInLastMonth(): ResponseEntity<List<CompanyResponse>> {
        val companies = companyService.getCompaniesRegisteredInLastMonth()
        return ResponseEntity.ok(companies)
    }

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterCompanyRequest): ResponseEntity<CompanyResponse> {
        val company = companyService.register(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(company)
    }
}