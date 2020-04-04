package ru.somarov.auth_service.backend.config.flyway

import org.flywaydb.core.Flyway
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.sql.Connection

object FlywayConfig {

    fun flyway(url:String, username:String, password:String): Flyway {
        return Flyway.configure()
                .dataSource(url, username, password)
                .baselineOnMigrate(true)
                .schemas("public")
                .load()
    }
}