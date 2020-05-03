package ru.somarov.auth_service.backend.db.repository

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.somarov.auth_service.backend.db.entity.Role
import ru.somarov.auth_service.backend.db.entity.UserAccount
import java.util.*

interface RoleRepo : ReactiveCrudRepository<Role, Short> {
    @Query("SELECT Role.id, Role.name " +
            "FROM \"Role\" INNER JOIN UserRole ON " +
            "Role.id = UserRole.idRole" +
            "WHERE UserRole.idUser = :id")
    fun findAllByUserAccountId(id: UUID): Flux<Role>
}