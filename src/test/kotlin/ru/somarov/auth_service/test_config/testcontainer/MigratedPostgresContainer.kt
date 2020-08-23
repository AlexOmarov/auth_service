package ru.somarov.auth_service.test_config.testcontainer

import org.testcontainers.containers.PostgreSQLContainer
import ru.somarov.auth_service.test_config.testcontainer.flyway.FlywayConfig


/**
 *
 *  @author AlexOmarov
 *  @date 04.04.2020
 *  @version 1.0.0
 *  @since 1.0.0
 */
class MigratedPostgresContainer : PostgreSQLContainer<MigratedPostgresContainer>(IMAGE) {

    companion object {
        private var IMAGE: String = "postgres:latest"
        private var migratedPostgresContainer: MigratedPostgresContainer? = null

        fun getInstance(): MigratedPostgresContainer {
            if (migratedPostgresContainer == null) {
                migratedPostgresContainer = MigratedPostgresContainer().apply {
                    withDatabaseName("auth_service")
                    withUsername("auth_service")
                    withPassword("auth_service")
                }
            }
            return migratedPostgresContainer as MigratedPostgresContainer
        }
    }

    override fun start() {
        try {
            super.start()
            //TODO: uuid-ossp
            FlywayConfig.flyway(jdbcUrl, username, password).migrate()
        } catch (e: Exception) {
            print(e)
        }
    }

}