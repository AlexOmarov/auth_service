package ru.somarov.auth_service.backend.db.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import ru.somarov.auth_service.backend.db.entity.Privilege

interface PrivilegeRepo: ReactiveCrudRepository<Privilege, Short>