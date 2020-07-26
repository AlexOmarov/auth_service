/*
package ru.somarov.auth_service.backend.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*


*/
/**
 * Имплементация {@link UserDetails UserDetails} - класс, несущий в себе информацию о пользователе.
 * При аутентификации экземплярс данными пользователя доступен из контекста.
 *
 *  @author AlexOmarov
 *  @date 24.02.2020
 *  @version 1.0.0
 *  @since 1.0.0
 *//*


class UserDetailsImpl(private val email: String,
                      private val password: String,
                      private val accountNonExpired: Boolean,
                      private val accountNonLocked: Boolean,
                      private val credentialsNonExpired: Boolean,
                      private val enabled: Boolean,
                      private val authorities: MutableCollection<out GrantedAuthority>) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return authorities
    }


    override fun isEnabled(): Boolean {
        return enabled
    }

    override fun getUsername(): String {
        return email
    }

    override fun isCredentialsNonExpired(): Boolean {
        return credentialsNonExpired
    }

    override fun getPassword(): String {
        return password
    }

    override fun isAccountNonExpired(): Boolean {
        return accountNonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return accountNonLocked
    }

}
*/
