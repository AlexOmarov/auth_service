package ru.somarov.auth_service.backend.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.somarov.auth_service.backend.config.log.Loggable
import ru.somarov.auth_service.backend.db.entity.Account
import ru.somarov.auth_service.backend.db.entity.Privilege
import ru.somarov.auth_service.backend.db.entity.Role
import ru.somarov.auth_service.backend.db.repository.AccountRepo
import ru.somarov.auth_service.backend.db.repository.PrivilegeRepo
import ru.somarov.auth_service.backend.db.repository.RoleRepo


@RestController
class HomeController {

    @Autowired
    private lateinit var roleRepo: RoleRepo

    @Autowired
    private lateinit var accountRepo: AccountRepo

    @Autowired
    private lateinit var privilegeRepo: PrivilegeRepo

    @Loggable
    @GetMapping("/greeting", produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
    fun greeting(): Mono<String> {
        return Mono.just("Response")
    }

    @Loggable
    @GetMapping("/privileges", produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
    fun getPrivileges(): Flux<Privilege> {
        return privilegeRepo.findAll()
    }

    @Loggable
    @GetMapping("/accounts", produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
    fun getAccounts(): Flux<Account> {
        return accountRepo.findAll()
    }

    @GetMapping("/roles", produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
    fun getRoles(): Flux<Role> {
        return roleRepo.findAll()
    }
}