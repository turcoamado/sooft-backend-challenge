import com.soofttechnology.challenge.ChallengeApplication
import com.soofttechnology.challenge.infrastructure.persistence.entity.CompanyEntity
import com.soofttechnology.challenge.infrastructure.persistence.entity.TransactionEntity
import com.soofttechnology.challenge.infrastructure.persistence.repository.impl.CompanyRepositoryImpl
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import java.time.LocalDateTime

@SpringBootTest(classes = [ChallengeApplication::class])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = ["classpath:application-test.properties"])
class CompanyRepositoryImplTest {

    @Autowired
    private lateinit var companyRepository: CompanyRepositoryImpl

    @Autowired
    private lateinit var entityManager: EntityManager

    @BeforeEach
    fun setup() {
        val company1 = CompanyEntity(cuit = "30-12345678-1", companyName = "Company Test A", registrationDate = LocalDateTime.now().minusDays(10))
        val company2 = CompanyEntity(cuit = "30-87654321-0", companyName = "Company Test B", registrationDate = LocalDateTime.now().minusDays(230))
        entityManager.persist(company1)
        entityManager.persist(company2)
        entityManager.flush()

        val transaction1 = TransactionEntity(company = company1, amount = 500.00, debitAccount = "ACC-1001", creditAccount = "CA-2001", date = LocalDateTime.now().minusDays(5))
        val transaction2 = TransactionEntity(company = company1, amount = 7500.00, debitAccount = "ACC-1001", creditAccount = "CA-2001", date = LocalDateTime.now().minusDays(35))
        val transaction3 = TransactionEntity(company = company2, amount = 2500.00, debitAccount = "ACC-2001", creditAccount = "CA-1001", date = LocalDateTime.now().minusDays(45))
        entityManager.persist(transaction1)
        entityManager.persist(transaction2)
        entityManager.persist(transaction3)

        entityManager.flush()
        entityManager.clear()
    }

    @Test
    @Transactional
    fun `should return companies with transactions in last month`() {
        val result = companyRepository.getCompaniesWithTransactionsInLastMonth()
        assertEquals(1, result.size)
        assertEquals("Company Test A", result[0].companyName)
    }

    @Test
    @Transactional
    fun `should return companies registered in last month`() {
        val result = companyRepository.getCompaniesRegisteredInLastMonth()
        assertEquals(1, result.size)
        assertEquals("Company Test A", result[0].companyName)
    }
}
