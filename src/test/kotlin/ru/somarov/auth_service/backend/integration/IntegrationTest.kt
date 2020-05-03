package ru.somarov.auth_service.backend.integration


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
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.testcontainers.junit.jupiter.Container
import reactor.test.StepVerifier
import ru.somarov.auth_service.backend.db.entity.Privilege
import ru.somarov.auth_service.backend.db.entity.Role
import ru.somarov.auth_service.backend.db.entity.UserAccount
import ru.somarov.auth_service.backend.db.repository.*
import ru.somarov.auth_service.test_config.EmbeddedPostgres
import ru.somarov.auth_service.test_config.testcontainer.MigratedPostgresContainer
import java.util.*


@DataR2dbcTest
@EmbeddedPostgres
@ActiveProfiles(profiles = ["test"])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith(SpringRunner::class)
class IntegrationTest {

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var privilegeRepo: PrivilegeRepo

    @Autowired
    private lateinit var roleRepo: RoleRepo

    @Autowired
    private lateinit var userAccountRepo: UserAccountRepo

    @Autowired
    private lateinit var rolePrivilegeRepo: RolePrivilegeRepo

    @Autowired
    private lateinit var userRoleRepo: UserRoleRepo




    private val privileges = listOf(Privilege("READ"), Privilege("WRITE"))
    private val roles = listOf(Role("ADMIN"), Role("USER"))
    private val userAccounts = listOf(
            UserAccount(
                    UUID(12,12),
                    "Alex",
                    "Omarov",
                    "shtil.a@yandex.ru",
                    passwordEncoder.encode("111222")
            ),
            UserAccount(
                    UUID(12,12),
                    "Bob",
                    "Kachinsky",
                    "dev@yandex.ru",
                    passwordEncoder.encode("111333")
            )
    )

    @BeforeAll
    internal fun fillDb() {
        Assume.assumeTrue(postgres.isRunning)
        LOGGER.info("BEFORE!!!")
        privilegeRepo.saveAll(privileges)
                .zipWith(userAccountRepo.saveAll(userAccounts))
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

        @Container
        val postgres = MigratedPostgresContainer.getInstance()

        private val LOGGER: Logger = getLogger(IntegrationTest::class.java)

        internal class Initializer: ApplicationContextInitializer<ConfigurableApplicationContext> {
            override fun initialize(applicationContext: ConfigurableApplicationContext) {
                if(!postgres.isRunning) {
                    try {
                        postgres.start()
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
}