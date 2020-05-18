package ru.somarov.auth_service.backend.db.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("auth_service.account")
data class Account(
        @Id
        var id: UUID? = null,
        var email: String,
        var password: String = "",
        var accountNonExpired: Boolean? = false,
        var accountNonLocked: Boolean? = false,
        var credentialsNonExpired: Boolean? = false,
        var enabled: Boolean? = false
)

