package ru.somarov.auth_service.backend.integration

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.testcontainers.junit.jupiter.Testcontainers
import reactor.test.StepVerifier
import ru.somarov.auth_service.backend.db.entity.Account
import ru.somarov.auth_service.backend.db.entity.Privilege
import ru.somarov.auth_service.backend.db.entity.Role
import ru.somarov.auth_service.backend.db.repository.*
import ru.somarov.auth_service.test_config.testcontainer.MigratedPostgresContainer


@DataR2dbcTest
@DirtiesContext
@RunWith(SpringRunner::class)
@ActiveProfiles(profiles = ["test"])
@Testcontainers(disabledWithoutDocker = false) //If we want to ignore this test without docker
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = [DataR2DBCIntegrationTest.Companion.Initializer::class])
class DataR2DBCIntegrationTest {

    private var passwordEncoder = BCryptPasswordEncoder()

    @Autowired
    private lateinit var privilegeRepo: PrivilegeRepo

    @Autowired
    private lateinit var roleRepo: RoleRepo

    @Autowired
    private lateinit var accountRepo: AccountRepo

    @Autowired
    private lateinit var rolePrivilegeRepo: RolePrivilegeRepo

    @Autowired
    private lateinit var accountRoleRepo: AccountRoleRepo

    private val privileges = listOf(Privilege("READ1"), Privilege("WRITE1"))
    private val roles = listOf(Role("ADMIN1"), Role("USER1"))
    private val userAccounts = listOf(
            Account(email = "shtil.a@yandex.ru",
                    password = passwordEncoder.encode("111222")
            ),
            Account(email = "dev@yandex.ru",
                    password = passwordEncoder.encode("111333"))
    )


    @BeforeAll
    internal fun fillDb() {
        //Assume.assumeTrue(postgres.isRunning)
        LOGGER.info("BEFORE!!!")
        privilegeRepo.deleteAll().block()
        privilegeRepo.saveAll(privileges)
                //.zipWith(accountRepo.saveAll(userAccounts))
                .zipWith(roleRepo.saveAll(roles))
                .subscribe()
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
                .expectNextCount(2)
                .expectComplete()
                .verify()
    }

    companion object {

        // We had to pass Junit5 support for TestContainers, because DataR2dbcTest loads
        // h2 database before postgres container has been started. So at the time app context
        // will be initializing postgres won't have been created and runned. So we had to do it by ourselves
        // manually started container
        // @Container
        val postgres = MigratedPostgresContainer.getInstance()

        private val LOGGER: Logger = getLogger(DataR2DBCIntegrationTest::class.java)

        internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
            override fun initialize(applicationContext: ConfigurableApplicationContext) {
                // Here we are trying to configure postgres. If it cannot be launched by any reason AND if
                // @Testcontainers(disabledWithoutDocker = false), then we just continue test with h2
                // If we will agree, that we always want to ignore tests without postgres, then we can remove
                // try block. And "if (!postgres.isRunning)" block is needed because we have singleton container,
                // which can be launched already in other tests
                if (!postgres.isRunning) {
                    try {
                        //Here is the workaround - we start postgres container manually while init app context
                        /*postgres.start()
                        TestPropertyValues.of(
                                "spring.r2dbc.url=" + postgres.jdbcUrl.replace("jdbc", "r2dbc"),
                                "spring.flyway.url=" + postgres.jdbcUrl,
                                "spring.r2dbc.platform=postgres"
                        ).applyTo(applicationContext.environment)*/
                    } catch (e: Exception) {
                        print(e)
                    }
                } /*else {
                    TestPropertyValues.of(
                            "spring.r2dbc.url=" + postgres.jdbcUrl.replace("jdbc", "r2dbc"),
                            "spring.flyway.url=" + postgres.jdbcUrl,
                            "spring.r2dbc.platform=postgres"
                    ).applyTo(applicationContext.environment)
                }*/
            }

        }
    }
}