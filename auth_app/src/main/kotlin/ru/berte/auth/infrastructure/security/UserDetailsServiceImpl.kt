package ru.berte.auth.infrastructure.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.*
import org.springframework.security.crypto.password.PasswordEncoder
import reactor.core.publisher.Mono
import ru.berte.auth.infrastructure.db.entity.Account
import ru.berte.auth.infrastructure.db.entity.Role
import ru.berte.auth.infrastructure.db.repository.AccountRepo
import ru.berte.auth.infrastructure.db.repository.PrivilegeRepo
import ru.berte.auth.infrastructure.db.repository.RoleRepo
import java.util.*

class UserDetailsServiceImpl(
    private val accountRepo: AccountRepo,
    private val roleRepo: RoleRepo,
    private val privilegeRepo: PrivilegeRepo,
    private val passwordEncoder: PasswordEncoder
) : ReactiveUserDetailsService {
    suspend fun register(
        email: String,
        password: String,
        role: Role,
        name: String
    ): Account {
        return accountRepo.save(
            Account(
                id = UUID(12L, 12L),
                email = email,
                password = passwordEncoder.encode(password),
                accountNonExpired = true,
                accountNonLocked = true,
                credentialsNonExpired = true,
                enabled = true
            )
        )
    }

    override fun findByUsername(email: String): Mono<UserDetails> {
        return accountRepo.findByEmail(email).flatMap { user ->
            user.id?.let {
                roleRepo.findAllByAccountId(it).collectList()
                    .map { roles ->
                        user.roles = roles
                        user
                    }
            }
        }

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
