package ru.somarov.auth_service.backend

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.PostgreSQLContainer
import ru.somarov.auth_service.backend.config.flyway.FlywayConfig


/**
 *
 *  @author AlexOmarov
 *  @date 04.04.2020
 *  @version 1.0.0
 *  @since 1.0.0
 */
class PostgresTestContainer: PostgreSQLContainer<PostgresTestContainer>(IMAGE)  {

    companion object {

        private var IMAGE: String = "postgres:latest"
        private var postgresContainer: PostgresTestContainer? = null

        fun getInstance(): PostgresTestContainer? {

            if (postgresContainer == null) {
                try {
                    postgresContainer = PostgresTestContainer().apply {
                        withDatabaseName("auth_service")
                        withUsername("auth_service")
                        withPassword("auth_service")
                    }
                } catch(e: Exception) {
                    print(e)
                }
            }

            return postgresContainer
        }

    }

    override fun stop() {
        //do nothing, JVM handles shut down
    }
}