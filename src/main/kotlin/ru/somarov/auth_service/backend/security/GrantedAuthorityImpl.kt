
package ru.somarov.auth_service.backend.security

import org.springframework.security.core.GrantedAuthority


/**
 *  Имплементация интерфейса {@link GrantedAuthority GrantedAuthority} Spring Security для определения
 *  привилегий ролей.
 *
 *  @author AlexOmarov
 *  @date 24.02.2020
 *  @version 1.0.0
 *  @since 1.0.0
 */

class GrantedAuthorityImpl(private val authority: String): GrantedAuthority {
    override fun getAuthority(): String {
        return authority
    }
}
