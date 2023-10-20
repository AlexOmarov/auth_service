package ru.berte.auth.infrastructure.db.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import ru.berte.auth.infrastructure.db.entity.AccountRole
import java.util.*

interface AccountRoleRepo : CoroutineCrudRepository<AccountRole, UUID>