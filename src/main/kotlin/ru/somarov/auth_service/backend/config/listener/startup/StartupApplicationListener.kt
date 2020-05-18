package ru.somarov.auth_service.backend.config.listener.startup

import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Profile
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import ru.somarov.auth_service.backend.config.flyway.FlywayConfig
import ru.somarov.auth_service.backend.db.entity.Account
import ru.somarov.auth_service.backend.db.entity.Privilege
import ru.somarov.auth_service.backend.db.entity.Role
import ru.somarov.auth_service.backend.db.repository.AccountRepo
import ru.somarov.auth_service.backend.db.repository.AccountRoleRepo
import ru.somarov.auth_service.backend.db.repository.PrivilegeRepo
import ru.somarov.auth_service.backend.db.repository.RoleRepo
import java.util.*


/**
 *
 *  @author AlexOmarov
 *  @date 04.04.2020
 *  @version 1.0.0
 *  @since 1.0.0
 */
@Component
@Profile("!test")
class StartupApplicationListener : ApplicationListener<ContextRefreshedEvent?> {

    @Autowired
    private lateinit var privilegeRepo: PrivilegeRepo

    @Autowired
    private lateinit var roleRepo: RoleRepo

    @Autowired
    private lateinit var accountRepo: AccountRepo

    @Autowired
    private lateinit var accountRoleRepo: AccountRoleRepo

    private val passwordEncoder = BCryptPasswordEncoder()


    @Value("\${spring.flyway.user}")
    private var username: String = ""

    @Value("\${spring.flyway.password}")
    private var password: String = ""

    @Value("\${spring.flyway.url}")
    private var url: String = ""

    private val privileges = listOf(Privilege("READ1"), Privilege("WRITE1"))
    private val roles = listOf(Role("ADMIN1"), Role("USER1"))
    private val userAccounts = listOf(
            Account(email = "shtil.a@yandex.ru",
                    password = passwordEncoder.encode("111222")
            ),
            Account(email = "dev@yandex.ru",
                    password = passwordEncoder.encode("111333"))
    )



    companion object {
        private val LOG: Logger = getLogger(StartupApplicationListener::class.java)
    }

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
       /* privilegeRepo.saveAll(privileges)
                //.zipWith(accountRepo.saveAll(userAccounts))
                .zipWith(roleRepo.saveAll(roles))
                .zipWith(accountRepo.save(userAccounts[1]))
                .subscribe()*/
        //FlywayConfig.flyway(url = url,password = password,username = username).migrate()
    }
}