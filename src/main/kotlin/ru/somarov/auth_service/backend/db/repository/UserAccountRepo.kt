package ru.somarov.auth_service.backend.db.repository

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import ru.somarov.auth_service.backend.db.entity.UserAccount
import java.util.*

interface UserAccountRepo: ReactiveCrudRepository<UserAccount, UUID> {

    @Query("SELECT * FROM \"user\" WHERE username = :username")
    fun findByUsername(username: String): Mono<UserAccount>
}