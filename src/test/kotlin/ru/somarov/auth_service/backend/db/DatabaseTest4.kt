package ru.somarov.auth_service.backend.db

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.testcontainers.junit.jupiter.Testcontainers


@SpringBootTest
@ActiveProfiles(profiles = ["test"])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith(SpringRunner::class)
class DatabaseTest4 {

    companion object {
        private val LOGGER:Logger = getLogger(DatabaseTest4::class.java)

    }

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
        Thread.sleep(15000)
    }

}