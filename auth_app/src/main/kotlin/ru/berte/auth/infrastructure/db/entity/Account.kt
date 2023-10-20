package ru.berte.auth.infrastructure.db.entity

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("account")
data class Account(
    @Id
    private var id: UUID,
    var email: String,
    var password: String,
    var accountNonExpired: Boolean,
    var accountNonLocked: Boolean,
    var credentialsNonExpired: Boolean,
    var enabled: Boolean
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
