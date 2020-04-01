package ru.somarov.auth_service.backend.db.entity

import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table
data class UserAccount(
        var uuid: UUID? = null,
        var username: String? = null,
        var email: String,
        var password: String = "",
        var accountNonExpired: Boolean? = false,
        var accountNonLocked: Boolean? = false,
        var credentialsNonExpired: Boolean? = false,
        var enabled: Boolean? = null,
        var phone: String? = null

)

