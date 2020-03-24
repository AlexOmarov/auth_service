package ru.somarov.auth_service.backend.db.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
data class UserRole(
        var roleId: Short,
        var userId: Short
) {
    @Id
    var id: Int? = null
}

