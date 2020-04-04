package ru.somarov.auth_service.backend.config.flyway

import org.flywaydb.core.Flyway
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.sql.Connection


class FlywayConfig {


    @Value("\${fw.url}")
    val url: String? = null

    @Value("\${fw.password}")
    val password: String? = null

    @Value("\${fw.username}")
    val username: String? = null

    fun flyway(): Flyway {
        return Flyway.configure()
                .dataSource(url, username, password)
                .baselineOnMigrate(true)
                .schemas("public")
                .load()
    }

    fun flyway(connection: Connection): Flyway {
        return Flyway.configure()
                .dataSource(url, username, password)
                .baselineOnMigrate(true)
                .schemas("public")
                .load()
    }
}