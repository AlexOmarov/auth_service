package ru.berte.auth.infrastructure.db.entity

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("account_role")
data class AccountRole(
    @Id
    private var id: UUID,
    var idRole: Short,
    var idAccount: Short
) : Persistable<UUID>, java.io.Serializable {
    @Transient
    var new: Boolean = true

    override fun getId(): UUID {
        return id
    }

    fun setId(id: UUID) {
        this.id = id
    }

    override fun isNew(): Boolean {
        return new
    }
}
