package ru.somarov.auth_service.backend.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.somarov.auth_service.backend.db.entity.Privilege
import ru.somarov.auth_service.backend.db.entity.Role
import ru.somarov.auth_service.backend.db.entity.Account
import ru.somarov.auth_service.backend.db.repository.PrivilegeRepo
import ru.somarov.auth_service.backend.db.repository.RoleRepo
import ru.somarov.auth_service.backend.db.repository.AccountRepo
import ru.somarov.auth_service.backend.security.UserDetailsServiceImpl
import java.time.Duration


@RestController
class HomeController {

    @Autowired
    private lateinit var roleRepo: RoleRepo

    @Autowired
    private lateinit var userDetailsServiceImpl: UserDetailsServiceImpl

    @Autowired
    private lateinit var accountRepo: AccountRepo

    @Autowired
    private lateinit var privilegeRepo: PrivilegeRepo


    @GetMapping("/")
    fun greet(str: Mono<String>): Mono<String> {
        return Mono.just("Response")
    }

    @GetMapping("/register")
    fun register(): Mono<Account>? {

        return roleRepo.findById(1)
                .flatMap { role: Role ->
                    userDetailsServiceImpl.registerUser(
                            "decentboat@gmail.com",
                            "111",
                            role,
                            "DecentBoat")
                }
    }

    @GetMapping("/privilege", produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
    fun getPrivilege(): Flux<Privilege> {
        return privilegeRepo.findAll().delayElements(Duration.ofSeconds(1))
    }

    @GetMapping("/user/{email}")
    fun getUser(@PathVariable("email") email: String): Mono<Account> {
        return accountRepo.findByEmail(email)
    }
}