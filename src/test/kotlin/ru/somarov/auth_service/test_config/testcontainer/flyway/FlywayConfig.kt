package ru.somarov.auth_service.test_config.testcontainer.flyway

import org.flywaydb.core.Flyway

object FlywayConfig {

    fun flyway(url: String, username: String, password: String): Flyway {
        return Flyway.configure()
                .dataSource(url, username, password)
                .baselineOnMigrate(true)
                .schemas("public")
                .load()
    }
}