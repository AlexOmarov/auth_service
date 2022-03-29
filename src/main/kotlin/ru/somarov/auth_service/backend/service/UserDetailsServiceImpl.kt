package ru.somarov.auth_service.backend.service

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.*
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.somarov.auth_service.backend.db.entity.Account
import ru.somarov.auth_service.backend.db.entity.Role
import ru.somarov.auth_service.backend.db.repository.AccountRepo
import ru.somarov.auth_service.backend.db.repository.PrivilegeRepo
import ru.somarov.auth_service.backend.db.repository.RoleRepo
import ru.somarov.auth_service.backend.security.GrantedAuthorityImpl
import ru.somarov.auth_service.backend.security.UserDetailsImpl
import java.util.*


/**
 *  Имплементация {@link UserDetailsService UserDetailsService} - класс, представляющий собой
 *  сервис аутентификации
 *  пользователей с переопределенным методом получения данных аутентифицированного пользователя.
 *
 *  @author AlexOmarov
 *  @date 24.02.2020
 *  @version 1.0.0
 *  @since 1.0.0
 */

class UserDetailsServiceImpl : ReactiveUserDetailsService {

    private val log = KotlinLogging.logger {}

    @Autowired
    private lateinit var accountRepo: AccountRepo

    @Autowired
    private lateinit var roleRepo: RoleRepo

    @Autowired
    private lateinit var privilegeRepo: PrivilegeRepo

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder


    /**
     * Регистрация новых пользователей
     * @param email email нового пользователя
     * @param password пароль нового пользоватля
     * @param role роль нового пользователя
     * @param name ник нового пользователя
     * @return новый пользователь из базы
     */

    fun registerUser(email: String,
                     password: String,
                     role: Role,
                     name: String): Mono<Account> {
        return accountRepo.save(
                Account(id = UUID(12L, 12L),
                        email = email,
                        password = passwordEncoder.encode(password),
                        accountNonExpired = true,
                        accountNonLocked = true,
                        credentialsNonExpired = true,
                        enabled = true))
    }


    /**
     *
     * Получение аутентифицированного пользователя
     * @param email email пользователя
     * @return UserDetailsImpl с заполненными данными пользователя
     * @throws UsernameNotFoundException Пользователь не найден
     */

    override fun findByUsername(email: String): Mono<UserDetails> {

        log.info { email }

        return accountRepo.findByEmail(email).flatMap { user ->
                    user.id?.let {
                        roleRepo.findAllByAccountId(it).collectList()
                                .map { roles ->
                                    user.roles = roles
                                    user } } }

                .map { account ->
                    val authorities: MutableList<GrantedAuthority> =
                        account.roles.map { GrantedAuthorityImpl("ROLE_${it.name}") }.toMutableList()

                    return@map UserDetailsImpl(
                            email = account.email,
                            password = account.password,
                            accountNonExpired = account.accountNonExpired,
                            accountNonLocked = account.accountNonLocked,
                            credentialsNonExpired = account.credentialsNonExpired,
                            enabled = account.enabled,
                            authorities = authorities
                    )
                }
    }
}
