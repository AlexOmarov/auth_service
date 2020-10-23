package ru.somarov.auth_service.backend.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain


/**
 *  Конфигурация Spring Security.
 *
 *  @author AlexOmarov
 *  @date 24.02.2020
 *  @version 1.0.0
 *  @since 1.0.0
 */

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig {

    /**
     * Инициализируем @Bean для кодирования паролей пользователей
     * Кодировка используется при записи паролей в базу
     * @return BCryptPasswordEncoder - кодировщик, использующий алгоритм BCrypt.
     */
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    /**
     * Определение параметров конфигурации Security
     */
    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        // TODO: enable only post login endpoint for login
        // TODO: find out if default authenticationManager is still in place and create reactive daoAuthenticationProvider
        return http
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .authorizeExchange()
                .pathMatchers("/login").permitAll()
                .pathMatchers("/actuator/*").permitAll()
                .pathMatchers("/**").authenticated()
                .and().logout().logoutUrl("/perform_logout")
                .and().build()
    }
    @Bean
    fun authManager(): ReactiveAuthenticationManager {
        return null
    }

}
