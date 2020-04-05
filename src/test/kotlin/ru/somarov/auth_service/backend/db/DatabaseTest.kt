package ru.somarov.auth_service.backend.db

import org.flywaydb.core.Flyway
import org.junit.ClassRule
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import reactor.test.StepVerifier
import ru.somarov.auth_service.backend.PostgresTestContainer
import ru.somarov.auth_service.backend.config.flyway.FlywayConfig
import ru.somarov.auth_service.backend.db.entity.Privilege
import ru.somarov.auth_service.backend.db.repository.PrivilegeRepo
import java.util.logging.Level
import java.util.logging.LogManager


@DataR2dbcTest
@ActiveProfiles(profiles = ["test"])
@Testcontainers
@DirtiesContext
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = [DatabaseTest.Companion.Initializer::class.java])
@RunWith(SpringRunner::class)
class DatabaseTest {

    companion object {

        private val LOGGER: Logger = LoggerFactory.getLogger(DatabaseTest::class.java)

        @ClassRule
        val postgres  = PostgresTestContainer.getInstance()

        internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
            override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
                if(postgres != null) {
                    TestPropertyValues.of(
                            "spring.r2dbc.url=" + postgres.jdbcUrl.replace("jdbc", "r2dbc"),
                            "spring.flyway.url=" + postgres.jdbcUrl
                    ).applyTo(configurableApplicationContext.environment)
                }
            }
        }
    }

    init {
        if(postgres != null && !postgres.isRunning)
            postgres.start()
    }

    @Autowired
    private lateinit var privilegeRepo: PrivilegeRepo

    @BeforeAll
    internal fun fillDb() {
        LOGGER.info("BEFORE!!!")
        //privilegeRepo.saveAll(listOf(Privilege(1,"READ"), Privilege(2, "WRITE"))).blockLast()
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