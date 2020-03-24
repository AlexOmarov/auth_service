package ru.somarov.auth_service.backend.db.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
data class RolePrivilege(
        var roleId: Short,
        var privilege: Short
) {
    @Id
    var id: Int? = null
}

