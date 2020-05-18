package ru.somarov.auth_service.backend.db.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("auth_service.account_role")
data class AccountRole(
        var idRole: Short,
        var idAccount: Short
) {
    @Id
    var id: Int? = null
}

