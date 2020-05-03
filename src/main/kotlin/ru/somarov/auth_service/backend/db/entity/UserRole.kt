package ru.somarov.auth_service.backend.db.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
data class UserRole(
        var idRole: Short,
        var idUserAccount: Short
) {
    @Id
    var id: Int? = null
}

