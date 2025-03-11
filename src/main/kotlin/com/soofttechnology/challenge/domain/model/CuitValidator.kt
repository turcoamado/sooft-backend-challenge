package com.soofttechnology.challenge.domain.model

object CuitValidator {
    private val cuitRegex = Regex("^(20|23|24|27|30|33|34)-\\d{8}-\\d$")

    fun validate(cuit: String): String? {
        if (!cuitRegex.matches(cuit)) {
            return "Invalid CUIT. Format is not correct, must be XX-XXXXXXXX-X and start with 20, 23, 24, 27, 30, 33 o 34."
        }

        val cleanCuit = cuit.replace("-", "")
        return if (!validateVerifierDigit(cleanCuit)) {
            "Invalid CUIT. The verifier digit is not valid."
        } else {
            null // Valid CUIT
        }
    }

    private fun validateVerifierDigit(cuit: String): Boolean {
        if (cuit.length != 11) return false

        val coefficients = listOf(5, 4, 3, 2, 7, 6, 5, 4, 3, 2)

        val baseCuit = cuit.substring(0, 10).map { it.digitToInt() }
        val actualVerifier = cuit[10].digitToInt()

        val sum = baseCuit.zip(coefficients).sumOf { (digit, coef) -> digit * coef }


        val remainder = sum % 11
        val calculatedDigit = when (remainder) {
            0 -> 0
            1 -> return false // CUIT not valid if the reminder is 1
            else -> 11 - remainder
        }

        /** testing only
        showPrintsForTesting(cuit, baseCuit, actualVerifier, calculatedDigit)
        **/

        return calculatedDigit == actualVerifier
    }

    private fun showPrintsForTesting(cuit: String, baseCuit: List<Int>, actualVerifier: Int, calculatedDigit: Int) {
        val coefficients = listOf(5, 4, 3, 2, 7, 6, 5, 4, 3, 2)

        println("CUIT in request: $cuit")
        println("base CUIT: $baseCuit")
        println("Real Verifier digit: $actualVerifier")

        val sum = baseCuit.mapIndexed { index, digit ->
            val product = digit * coefficients[index]
            println("(${digit} * ${coefficients[index]}) = $product")
            product
        }.sum()

        println("Sum: $sum")

        println("Calculated digit: $calculatedDigit")
    }
}