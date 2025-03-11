package com.soofttechnology.challenge.infrastructure.persistence.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "companies")
data class CompanyEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    var cuit: String,

    @Column(nullable = false)
    var companyName:String,

    @Column(nullable = false)
    var registrationDate: LocalDateTime? = null
)
