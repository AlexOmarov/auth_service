package ru.berte.auth.infrastructure.db.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import ru.berte.auth.infrastructure.db.entity.Account
import java.util.*

interface AccountRepo : CoroutineCrudRepository<Account, UUID>