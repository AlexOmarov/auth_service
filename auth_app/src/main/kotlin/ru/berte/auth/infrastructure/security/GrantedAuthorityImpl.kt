package ru.berte.auth.infrastructure.security

import org.springframework.security.core.GrantedAuthority

class GrantedAuthorityImpl(private val authority: String) : GrantedAuthority {
    override fun getAuthority(): String {
        return authority
    }
}
