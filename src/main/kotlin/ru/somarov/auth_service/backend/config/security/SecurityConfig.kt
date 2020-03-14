package ru.somarov.auth_service.backend.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.session.HttpSessionEventPublisher
import ru.somarov.auth_service.backend.security.UserDetailsServiceImpl


/**
 *  Конфигурация Spring Security.
 *
 *  @author AlexOmarov
 *  @date 24.02.2020
 *  @version 1.0.0
 *  @since 1.0.0
 */
/*@EnableReactiveMethodSecurity*/
@EnableWebFluxSecurity
class SecurityConfig {

    /**
     * Инициализируем @Bean для кодирования паролей пользователей
     * Кодировка используется при записи паролей в базу
     * @return BCryptPasswordEncoder - кодировщик, использующий алгоритм BCrypt.
     */
/*    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }*/

    /**
     * Определение параметров конфигурации Security
     */
    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http.authorizeExchange()
                .anyExchange()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .formLogin()
                .and().csrf().disable()
        return http.build()
    }




    /**
     * Подключение кастомной реализации UserDetailsService
     */

    @Bean
    fun userDetailsService(): ReactiveUserDetailsService {
        return UserDetailsServiceImpl()
    }


}