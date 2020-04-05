package ru.somarov.auth_service.backend.db

import org.junit.ClassRule
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.testcontainers.junit.jupiter.Testcontainers
import reactor.test.StepVerifier
import ru.somarov.auth_service.backend.PostgresTestContainer
import ru.somarov.auth_service.backend.db.repository.PrivilegeRepo


@DataR2dbcTest
@ActiveProfiles(profiles = ["test"])
@Testcontainers
@DirtiesContext
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith(SpringRunner::class)
class DatabaseTest2 {

    companion object {
        private val LOGGER:Logger = getLogger(DatabaseTest2::class.java)
        @ClassRule
        val postgres  = PostgresTestContainer.getInstance()
    }

    init {
        if(DatabaseTest.postgres != null && !DatabaseTest.postgres.isRunning)
            DatabaseTest.postgres.start()
    }

    @Autowired
    private lateinit var privilegeRepo: PrivilegeRepo

    @BeforeAll
    internal fun fillDb() {
        LOGGER.info("BEFORE!!!")
        //privilegeRepo.saveAll(listOf(Privilege(1, "READ"), Privilege(2, "WRITE"))).blockLast()
    }

    @AfterAll
    internal fun drop() {
        LOGGER.info("AFTER!!!")
        //privilegeRepo.deleteAll().block()
    }

    @Test
    fun `When calling findAll get all`() {
        StepVerifier.create(privilegeRepo.findAll())
                .expectSubscription()
                .expectNextCount(0)
                .expectComplete()
                .verify()
    }

}