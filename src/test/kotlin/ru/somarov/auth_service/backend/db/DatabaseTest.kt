package ru.somarov.auth_service.backend.db


import org.junit.Assume
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.DisabledIf
import org.springframework.test.context.junit4.SpringRunner
import org.testcontainers.DockerClientFactory
import org.testcontainers.dockerclient.DockerClientProviderStrategy
import org.testcontainers.junit.jupiter.Container
import reactor.test.StepVerifier
import ru.somarov.auth_service.backend.config.flyway.FlywayConfig
import ru.somarov.auth_service.backend.db.repository.PrivilegeRepo
import ru.somarov.auth_service.test_config.EmbeddedPostgres
import ru.somarov.auth_service.testcontainer.MigratedPostgresContainer


@DataR2dbcTest
@EmbeddedPostgres
@ActiveProfiles(profiles = ["test"])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith(SpringRunner::class)
class DatabaseTest {

    companion object {

        @Container
        val postgres = MigratedPostgresContainer.getInstance()

        private val LOGGER: Logger = getLogger(DatabaseTest::class.java)

        internal class Initializer: ApplicationContextInitializer<ConfigurableApplicationContext> {
            override fun initialize(applicationContext: ConfigurableApplicationContext) {
                    if(!postgres.isRunning) {
                        try {
                            postgres.start()
                            FlywayConfig.flyway(postgres.jdbcUrl,postgres.username,postgres.password)
                            TestPropertyValues.of(
                                    "spring.r2dbc.url=" + postgres.jdbcUrl.replace("jdbc", "r2dbc")
                            ).applyTo(applicationContext.environment)
                        } catch (e: Exception) {
                            print(e)
                        }
                    } else {
                        TestPropertyValues.of(
                                "spring.r2dbc.url=" + postgres.jdbcUrl.replace("jdbc", "r2dbc")
                        ).applyTo(applicationContext.environment)
                    }

            }

        }
    }

    @Autowired
    private lateinit var privilegeRepo: PrivilegeRepo

    @BeforeAll
    internal fun fillDb() {
        Assume.assumeTrue(postgres.isRunning)
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