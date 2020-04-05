package ru.somarov.auth_service.testcontainer

import org.testcontainers.containers.PostgreSQLContainer


/**
 *
 *  @author AlexOmarov
 *  @date 04.04.2020
 *  @version 1.0.0
 *  @since 1.0.0
 */
class PostgresTestContainer: PostgreSQLContainer<PostgresTestContainer>(IMAGE) {

    companion object {
        private var IMAGE: String = "postgres:latest"
        private var postgresContainer: PostgresTestContainer? = null

        fun getInstance(): PostgresTestContainer {
            if (postgresContainer == null) {
                postgresContainer = PostgresTestContainer().apply {
                    withDatabaseName("auth_service")
                    withUsername("auth_service")
                    withPassword("auth_service")
                }
            }
            return postgresContainer as PostgresTestContainer
        }
    }

    override fun start() {
        try {
            super.start()
        } catch (e: Exception) {
            print(e)
        }
    }

}