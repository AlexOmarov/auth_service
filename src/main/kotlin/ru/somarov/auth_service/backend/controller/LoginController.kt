package ru.somarov.auth_service.backend.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebSession
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.somarov.auth_service.backend.config.log.Loggable
import ru.somarov.auth_service.backend.db.entity.Account
import ru.somarov.auth_service.backend.db.entity.Privilege
import ru.somarov.auth_service.backend.db.entity.Role
import ru.somarov.auth_service.backend.db.repository.AccountRepo
import ru.somarov.auth_service.backend.db.repository.PrivilegeRepo
import ru.somarov.auth_service.backend.db.repository.RoleRepo
import ru.somarov.auth_service.backend.service.UserDetailsServiceImpl


@RestController
class LoginController {

    @Autowired
    private lateinit var authManager: ReactiveAuthenticationManager

    @Value("\${user.config}")
    private lateinit var config: String

    @Autowired
    private lateinit var roleRepo: RoleRepo

    @Autowired
    private lateinit var accountRepo: AccountRepo

    @Autowired
    private lateinit var privilegeRepo: PrivilegeRepo


    @Loggable
    @PostMapping("/login", produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
    fun login(swe: ServerWebExchange, authentication: Authentication): Mono<ResponseEntity<Any>> {
        val authenticatedObject = authManager.authenticate(authentication)
        return Mono.just(ResponseEntity(HttpStatus.ACCEPTED))
    }

}