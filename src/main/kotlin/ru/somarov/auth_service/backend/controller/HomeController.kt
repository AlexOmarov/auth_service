package ru.somarov.auth_service.backend.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


@RestController
class HomeController {
    @GetMapping("/")
    fun greet(str: Mono<String>): Mono<String> {
        return Mono.just("Response")
    }
}