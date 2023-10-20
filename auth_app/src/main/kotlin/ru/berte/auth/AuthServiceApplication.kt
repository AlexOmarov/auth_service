package ru.berte.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import reactor.core.publisher.Hooks

@SpringBootApplication
class AuthServiceApplication

fun main(args: Array<String>) {
    // enable tracing propagation between different reactive pipelines
    Hooks.enableAutomaticContextPropagation()
    @Suppress("SpreadOperator")
    runApplication<AuthServiceApplication>(*args)
}
