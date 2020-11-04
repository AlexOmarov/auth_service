package ru.somarov.auth_service.api

/**
 *
 *  @author AlexOmarov
 *  @date 04.11.2020
 *  @version 1.0.0
 *  @since 1.0.0
 */
data class LoginRequest(val username: String, val password: String)