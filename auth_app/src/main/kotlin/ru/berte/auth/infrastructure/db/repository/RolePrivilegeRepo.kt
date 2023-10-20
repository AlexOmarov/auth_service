package ru.berte.auth.infrastructure.db.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import ru.berte.auth.infrastructure.db.entity.RolePrivilege
import java.util.*

interface RolePrivilegeRepo : CoroutineCrudRepository<RolePrivilege, UUID>