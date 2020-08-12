package ru.somarov.auth_service.backend.db.repository

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import ru.somarov.auth_service.backend.db.entity.Privilege

interface PrivilegeRepo : ReactiveCrudRepository<Privilege, Short> {
    @Query("SELECT Privilege.id, Privilege.name " +
            "FROM \"Privilege\" INNER JOIN RolePrivilege ON " +
            "Privilege.id = RolePrivilege.idPrivilege " +
            "WHERE RolePrivilege.idRole in (:ids)")
    fun findAllByRolesId(ids: List<Short>): Flux<Privilege>
}