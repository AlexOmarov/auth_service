package ru.somarov.auth_service.backend.controller

/*import ru.somarov.auth_service.backend.security.UserDetailsServiceImpl*/
import io.opentracing.Span
import io.opentracing.Tracer
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.somarov.auth_service.backend.db.entity.Account
import ru.somarov.auth_service.backend.db.entity.Privilege
import ru.somarov.auth_service.backend.db.repository.AccountRepo
import ru.somarov.auth_service.backend.db.repository.PrivilegeRepo
import ru.somarov.auth_service.backend.db.repository.RoleRepo
import java.time.Duration


@RestController
class HomeController {

    private val log = KotlinLogging.logger {}

    @Qualifier("tracer")
    @Autowired
    private lateinit var tracer: Tracer

    @Autowired
    private lateinit var roleRepo: RoleRepo

    /*@Autowired
    private lateinit var userDetailsServiceImpl: UserDetailsServiceImpl*/

    @Autowired
    private lateinit var accountRepo: AccountRepo


    @Autowired
    private lateinit var privilegeRepo: PrivilegeRepo


    @GetMapping("/")
    fun greet(str: Mono<String>): Mono<String> {
        return Mono.just("Response")
    }

    @GetMapping("/privilege", produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
    fun getPrivilege(): Flux<Privilege> {
        val activeSpan = tracer.scopeManager().activeSpan()
        log.info { activeSpan }
        val scope = tracer.activateSpan(tracer.buildSpan("/privilege").start())
        log.info { tracer.scopeManager().activeSpan() }
        log.info { scope }
        tracer.activeSpan().finish()

        return privilegeRepo.findAll()
    }

    @GetMapping("/user/{email}")
    fun getUser(@PathVariable("email") email: String): Mono<Account> {
        return accountRepo.findByEmail(email)
    }
}