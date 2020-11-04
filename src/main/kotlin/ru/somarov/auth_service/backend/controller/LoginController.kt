package ru.somarov.auth_service.backend.controller

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import ru.somarov.auth_service.api.LoginRequest
import ru.somarov.auth_service.backend.config.log.Loggable


@RestController
class LoginController {

    @Loggable
    @PostMapping("/login", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun login(swe: ServerWebExchange): Mono<String> {
        return Mono.just("DONE")
    }

}