package ru.somarov.auth_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux
import ru.somarov.auth_service.backend.config.flyway.FlywayConfig



@SpringBootApplication
@EnableWebFlux
class AuthServiceApplication

fun main(args: Array<String>) {
    FlywayConfig().flyway().migrate()
    runApplication<AuthServiceApplication>(*args)
}



