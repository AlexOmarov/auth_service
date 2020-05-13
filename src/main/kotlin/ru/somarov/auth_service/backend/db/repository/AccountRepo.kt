package ru.somarov.auth_service.backend.db.repository

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import ru.somarov.auth_service.backend.db.entity.Account
import java.util.*

interface AccountRepo : ReactiveCrudRepository<Account, UUID> {
    @Query("SELECT * FROM \"Account\" WHERE email = :email")
    fun findByEmail(email: String): Mono<Account>
}