package ru.somarov.auth_service.backend.db

import org.junit.Test
import org.junit.jupiter.api.BeforeAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import reactor.test.StepVerifier
import ru.somarov.auth_service.backend.db.entity.Privilege
import ru.somarov.auth_service.backend.db.repository.PrivilegeRepo
import java.util.logging.Level
import java.util.logging.LogManager

@DataR2dbcTest
class DatabaseTest {

    companion object {
        init {
            // Postgres JDBC driver uses JUL; disable it to avoid annoying,
            // irrelevant, stderr logs during connection testing
            LogManager.getLogManager().getLogger("").level = Level.OFF
        }
    }

    @Autowired
    private lateinit var privilegeRepo: PrivilegeRepo

    @BeforeAll
    fun init() {
        privilegeRepo.saveAll(listOf(Privilege("READ"),Privilege("WRITE")))
    }

    @Test
    fun `When calling findAll get all`() {
        StepVerifier.create(privilegeRepo.findAll())
                .expectSubscription()
                .expectNextCount(2)
                .expectComplete()
                .verify()
    }
}