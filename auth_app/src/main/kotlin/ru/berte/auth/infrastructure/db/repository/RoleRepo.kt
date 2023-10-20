package ru.berte.auth.infrastructure.db.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import reactor.core.publisher.Flux
import ru.berte.auth.infrastructure.db.entity.Role
import java.util.*

interface RoleRepo : CoroutineCrudRepository<Role, UUID> {
    fun findAllByAccountId(id: UUID): Flux<Role>
}