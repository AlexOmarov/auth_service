package ru.somarov.auth_service.backend.db.repository

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import ru.somarov.auth_service.backend.db.entity.Role
import java.util.*

interface RoleRepo : ReactiveCrudRepository<Role, Short> {
    @Query("SELECT Role.id, Role.name " +
            "FROM \"Role\" INNER JOIN AccountRole ON " +
            "Role.id = AccountRole.idRole" +
            "WHERE AccountRole.idAccount = :id")
    fun findAllByUserAccountId(id: UUID): Flux<Role>
}