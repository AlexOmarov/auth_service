package ru.somarov.auth_service.backend.db

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import reactor.test.StepVerifier
import ru.somarov.auth_service.backend.config.flyway.FlywayConfig
import ru.somarov.auth_service.backend.db.repository.PrivilegeRepo
import ru.somarov.auth_service.testcontainer.PostgresTestContainer


@DataR2dbcTest
@ActiveProfiles(profiles = ["test"])
@Testcontainers
@DirtiesContext
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith(SpringRunner::class)
class DatabaseTest: ApplicationContextInitializer<ConfigurableApplicationContext> {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(DatabaseTest::class.java)
    }

    @Container
    val postgres = PostgresTestContainer.getInstance()

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

    override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
            if(!postgres.isRunning) {
                try {
                    postgres.start()
                    FlywayConfig.flyway(postgres.jdbcUrl,postgres.username,postgres.password)
                    TestPropertyValues.of(
                            "spring.r2dbc.url=" + postgres.jdbcUrl.replace("jdbc", "r2dbc")
                    ).applyTo(configurableApplicationContext.environment)
                } catch (e: Exception) {
                    print(e)
                }
            } else {
                TestPropertyValues.of(
                        "spring.r2dbc.url=" + postgres.jdbcUrl.replace("jdbc", "r2dbc")
                ).applyTo(configurableApplicationContext.environment)
            }
        

    }


}