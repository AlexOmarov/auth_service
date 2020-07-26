/*
package ru.somarov.auth_service.backend.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.ReactiveUserDetailsService

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.somarov.auth_service.backend.db.entity.Role
import ru.somarov.auth_service.backend.db.entity.Account
import ru.somarov.auth_service.backend.db.repository.PrivilegeRepo
import ru.somarov.auth_service.backend.db.repository.RoleRepo
import ru.somarov.auth_service.backend.db.repository.AccountRepo
import java.util.*


*/
/**
 *  Имплементация {@link UserDetailsService UserDetailsService} - класс, представляющий собой
 *  сервис аутентификации
 *  пользователей с переопределенным методом получения данных аутентифицированного пользователя.
 *
 *  @author AlexOmarov
 *  @date 24.02.2020
 *  @version 1.0.0
 *  @since 1.0.0
 *//*

*/
/*@Service
class UserDetailsServiceImpl : ReactiveUserDetailsService {


    @Autowired
    private lateinit var accountRepo: AccountRepo

    @Autowired
    private lateinit var roleRepo: RoleRepo

    @Autowired
    private lateinit var privilegeRepo: PrivilegeRepo

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder


    *//*
*/
/**
     * Регистрация новых пользователей
     * @param email email нового пользователя
     * @param password пароль нового пользоватля
     * @param role роль нового пользователя
     * @param name ник нового пользователя
     * @return новый пользователь из базы
     *//*
*/
/*

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

    *//*
*/
/**
     *
     * Получение аутентифицированного пользователя
     * @param email email пользователя
     * @return UserDetailsImpl с заполненными данными пользователя
     * @throws UsernameNotFoundException Пользователь не найден
     *//*
*/
/*

    override fun findByUsername(email: String): Mono<UserDetails> {
        return Mono.from {  }*//*
*/
/*accountRepo.findByEmail(email)
                .flatMap { acc ->
                                roleRepo
                                        .findAllByUserAccountId(acc.id!!)
                                        .collectList().flatMap { roles ->
                                            privilegeRepo
                                                    .findAllByRolesId(roles.map { it.id!! })
                                                    .collectList().map {
                                                        it.map { it.name }.union(roles.map { "ROLE_" + it.name }).map {
                                                            GrantedAuthorityImpl(it)
                                                        }.toMutableList()
                                                    }
                                        }.map { authorities ->
                                            UserDetailsImpl(acc.email, acc.password,
                                                    accountNonExpired = true, accountNonLocked = true, credentialsNonExpired = true,
                                                    enabled = true, authorities = authorities)
                                        }


               }*//*
*/
/*
    }
}*/
