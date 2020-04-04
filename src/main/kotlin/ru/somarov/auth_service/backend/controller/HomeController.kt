package ru.somarov.auth_service.backend.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.somarov.auth_service.backend.db.entity.Privilege
import ru.somarov.auth_service.backend.db.entity.UserAccount
import ru.somarov.auth_service.backend.db.repository.PrivilegeRepo
import ru.somarov.auth_service.backend.db.repository.RoleRepo
import ru.somarov.auth_service.backend.db.repository.UserAccountRepo
import ru.somarov.auth_service.backend.security.UserDetailsServiceImpl
import java.time.Duration
import javax.annotation.PostConstruct


@RestController
class HomeController {

    @Autowired
    private lateinit var roleRepo: RoleRepo

    @Autowired
    private lateinit var userDetailsServiceImpl: UserDetailsServiceImpl

    @Autowired
    private lateinit var userAccountRepo: UserAccountRepo

    @Autowired
    private lateinit var privilegeRepo: PrivilegeRepo



/*

    @PostConstruct
    fun init() {
        roleRepo.findById(1).block()?.let {
            userDetailsServiceImpl.registerUser("decentboat@gmail.com",
                    "11111", it, name = "Test_adm_2").block()?.uuid?.let {
                it1 -> println(userAccountRepo.findById(it1))
            }
        }
    }
*/



    @GetMapping("/")
    fun greet(str: Mono<String>): Mono<String> {
        return Mono.just("Response")
    }
    @GetMapping("/register")
    fun register(): Mono<UserAccount>? {
        val role = roleRepo.findById(1)
        return role.block()?.let { userDetailsServiceImpl.registerUser("decentboat@gmail.com","111", it,"DecentBoat") }
    }
    @GetMapping("/privilege", produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
    fun getPrivilege(): Flux<Privilege> {
        return privilegeRepo.findAll().delayElements(Duration.ofSeconds(1))
    }

    @GetMapping("/user/{username}")
    fun getUser(@PathVariable("username") username:String): Mono<UserAccount> {
        return userAccountRepo.findByUsername(username)
    }
}