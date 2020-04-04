package ru.somarov.auth_service.backend

import org.testcontainers.containers.PostgreSQLContainer
import ru.somarov.auth_service.backend.config.flyway.FlywayConfig

/**
 *
 *  @author AlexOmarov
 *  @date 04.04.2020
 *  @version 1.0.0
 *  @since 1.0.0
 */
class PostgresTestContainer {

    companion object {

        private var IMAGE: String = "postgres:latest"
        private var postgresContainer: PostgresContainer? = null

        fun getInstance(): PostgresContainer {

            if (postgresContainer == null) {
                postgresContainer = PostgresTestContainer().PostgresContainer().apply {
                    withDatabaseName("auth_service")
                    withUsername("auth_service")
                    withPassword("auth_service")
                }
            }

            return postgresContainer as PostgresContainer
        }
    }

    inner class PostgresContainer: PostgreSQLContainer<PostgresContainer>(IMAGE)  {
        override fun start() {
            super.start()
            FlywayConfig.flyway(jdbcUrl,username,password).migrate()
            System.setProperty("TEST_POSTGRES_URL", jdbcUrl.replace("jdbc","r2dbc"))
            System.setProperty("TEST_POSTGRES_USERNAME", username)
            System.setProperty("TEST_POSTGRES_PASSWORD", password)
        }

        override fun stop() {
            System.clearProperty("TEST_POSTGRES_URL")
            System.clearProperty("TEST_POSTGRES_USERNAME")
            System.clearProperty("TEST_POSTGRES_PASSWORD")
            //do nothing, JVM handles shut down
        }
    }
}