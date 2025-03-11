package com.soofttechnology.challenge.domain.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CuitValidatorTest {

    private lateinit var cuitValidator: CuitValidator

    @BeforeEach
    fun setup() {
        cuitValidator = CuitValidator
    }

    @Test
    fun `validate should return null for a valid CUIT`() {
        val validCuil = "30-12345678-1"
        assertNull(cuitValidator.validate(validCuil))
    }

    @Test
    fun `should return error message for CUIT with incorrect format`() {
        val invalidFormatCuits = listOf(
            "20123456789",      // No dashes
            "20-123456789-1",   // Too much numbers
            "35-12345678-1",    // Invalid prefix
            "30-1234A678-1"     // Non-numeric character
        )

        invalidFormatCuits.forEach { cuit ->
            assertEquals(cuitValidator.validate(cuit), "Invalid CUIT. Format is not correct, must be XX-XXXXXXXX-X and start with 20, 23, 24, 27, 30, 33 o 34.")
        }
    }

    @Test
    fun `should return error message for CUIT with invalid verifier digit`() {
        val invalidCuit = "20-12345678-2"
        assertEquals("Invalid CUIT. The verifier digit is not valid.", cuitValidator.validate(invalidCuit))
    }

}