package ru.berte.auth.infrastructure.db.repository

import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import ru.berte.auth.infrastructure.db.entity.Privilege
import java.util.*

interface PrivilegeRepo : CoroutineCrudRepository<Privilege, Short> {
    fun findAllByRoleIdIn(ids: List<UUID>): Flow<Privilege>
}