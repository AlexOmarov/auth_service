package ru.berte.auth.infrastructure.security

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactor.mono
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import reactor.core.publisher.Mono
import ru.berte.auth.infrastructure.db.repository.AccountRepo
import ru.berte.auth.infrastructure.db.repository.PrivilegeRepo
import ru.berte.auth.infrastructure.db.repository.RoleRepo

class UserDetailsServiceImpl(
    private val accountRepo: AccountRepo,
    private val roleRepo: RoleRepo,
    private val privilegeRepo: PrivilegeRepo
) : ReactiveUserDetailsService {
    override fun findByUsername(email: String): Mono<UserDetails?> {
        return mono {
            val account = accountRepo.findAccountByEmail(email) ?: return@mono null
            val roles = roleRepo.findAllByAccountId(account.id)
            val privileges = privilegeRepo.findAllByRoleIdIn(roles.map { it.id }.toList())
            val authorities = roles.map { GrantedAuthorityImpl("ROLE_${it.name}") }.toList() +
                    privileges.map { GrantedAuthorityImpl(it.name) }.toList()
            return@mono UserDetailsImpl(
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
