package ru.somarov.auth_service.backend.integration

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import reactor.test.StepVerifier
import ru.somarov.auth_service.backend.db.repository.PrivilegeRepo
import ru.somarov.auth_service.test_config.testcontainer.MigratedPostgresContainer

@SpringBootTest
@DirtiesContext
@RunWith(SpringRunner::class)
@ActiveProfiles(profiles = ["test"])
@Testcontainers(disabledWithoutDocker = true)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = [DataR2DBCIntegrationTest.Companion.Initializer::class])
class SpringBootIntegrationTest {

    @Autowired
    private lateinit var privilegeRepo: PrivilegeRepo

    @BeforeAll
    internal fun fillDb() {

    }

    @AfterAll
    internal fun drop() {

    }

    @Test
    fun `When calling findAll get all`() {
        StepVerifier.create(privilegeRepo.findAll())
                .expectSubscription()
                .expectNextCount(2)
                .expectComplete()
                .verify()
    }

    companion object {

        @Container
        val postgres = MigratedPostgresContainer.getInstance()

        internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
            override fun initialize(applicationContext: ConfigurableApplicationContext) {
                TestPropertyValues.of(
                        "spring.r2dbc.url=" + postgres.jdbcUrl.replace("jdbc", "r2dbc"),
                        "spring.flyway.url=" + postgres.jdbcUrl,
                        "spring.r2dbc.platform=postgres"
                ).applyTo(applicationContext.environment)
            }

        }
    }
}