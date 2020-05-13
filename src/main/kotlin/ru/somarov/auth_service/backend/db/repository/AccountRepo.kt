package ru.somarov.auth_service.backend.db.repository

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import ru.somarov.auth_service.backend.db.entity.Account
import java.util.*

interface AccountRepo : ReactiveCrudRepository<Account, UUID> {
    @Query("SELECT * FROM Account WHERE email = :email")
    fun findByEmail(email: String): Mono<Account>

    @Query("INSERT INTO Account values(:account.id," +
            " :account.email," +
            " :account.password," +
            " :account.accountNonExpired," +
            " :account.accountNonLocked," +
            " :account.credentialsNonExpired," +
            " :account.enabled" +
            ")")
    fun insert(account: Account): Mono<Account>

    @Query("UPDATE Account SET " +
            " email=:account.email," +
            " password=:account.password," +
            " accountNonExpired=:account.accountNonExpired," +
            " accountNonLocked=:account.accountNonLocked," +
            " credentialsNonExpired=:account.credentialsNonExpired," +
            " enabled=:account.enabled" +
            " WHERE id=:account.id" +
            ")")
    fun update(account: Account): Mono<Account>
}