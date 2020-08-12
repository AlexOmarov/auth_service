package ru.somarov.auth_service.backend.db.repository

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import ru.somarov.auth_service.backend.db.entity.Role
import java.util.*

interface RoleRepo : ReactiveCrudRepository<Role, Short> {
    @Query("SELECT role.id, role.name " +
            "FROM \"role\" INNER JOIN account_role ON " +
            "role.id = account_role.id_role " +
            "WHERE account_role.id_account = :id")
    fun findAllByAccountId(id: UUID): Flux<Role>
}