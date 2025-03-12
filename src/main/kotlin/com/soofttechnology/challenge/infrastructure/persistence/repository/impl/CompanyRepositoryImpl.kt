package com.soofttechnology.challenge.infrastructure.persistence.repository.impl

import com.soofttechnology.challenge.infrastructure.persistence.entity.CompanyEntity
import com.soofttechnology.challenge.infrastructure.persistence.repository.CompanyRepositoryCustom
import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class CompanyRepositoryImpl(
    @PersistenceContext
    private val entityManager: EntityManager
) : CompanyRepositoryCustom {

    @Value("\${company.query.lastDays}")
    private val lastDays: Long = 30
    private lateinit var startDate: LocalDateTime

    @PostConstruct
    fun init() {
        startDate = LocalDateTime.now().minusDays(lastDays)
    }

    override fun getCompaniesWithTransactionsInLastMonth(): List<CompanyEntity> {
        val query = """
            SELECT DISTINCT c 
            FROM CompanyEntity c 
            JOIN TransactionEntity t ON c.id = t.company.id
            WHERE t.date >= :startDate
        """.trimIndent()

        return entityManager.createQuery(query, CompanyEntity::class.java)
            .setParameter("startDate", startDate)
            .resultList
    }

    override fun getCompaniesRegisteredInLastMonth(): List<CompanyEntity> {
        val query = """
            SELECT DISTINCT c 
            FROM CompanyEntity c 
            WHERE c.registrationDate >= :startDate
        """.trimIndent()

        return entityManager.createQuery(query, CompanyEntity::class.java)
            .setParameter("startDate", startDate)
            .resultList
    }
}