package ru.somarov.auth_service.backend.config.flyway

import org.flywaydb.core.Flyway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


class FlywayConfig {


    fun flyway(): Flyway {
        return Flyway.configure()
                .dataSource("jdbc:postgresql://193.187.174.125:5432/auth_service", "auth_service", "auth_service")
                .baselineOnMigrate(true)
                .schemas("public")
                .load()
    }
}