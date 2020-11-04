package ru.somarov.auth_service.backend.config.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerFormLoginAuthenticationConverter
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher
import org.springframework.session.data.redis.ReactiveRedisSessionRepository
import reactor.core.publisher.Mono
import ru.somarov.auth_service.backend.service.UserDetailsServiceImpl


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
        return http
                .addFilterAt(authenticationWebFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .authorizeExchange()
                // Path matchers relates to authorization filter
                // Because I decided to authenticate user with custom filter on login,
                // if we will call post login without form data it will be 401 code
                // On all other endpoints will be only authorization filter, which will return 401/403 code
                .pathMatchers("/login").permitAll()
                .pathMatchers("/actuator/*").permitAll()
                .pathMatchers("/**").authenticated()
                .and().logout().logoutUrl("/perform_logout").logoutSuccessHandler {
                    webFilterExchange: WebFilterExchange, _: Authentication -> run {
                    webFilterExchange.exchange.response.statusCode = HttpStatus.OK
                }
                    Mono.empty<Void>()
                }
                .and().build()
    }

    @Bean
    fun authenticationWebFilter(): AuthenticationWebFilter {
        val authenticationWebFilter = AuthenticationWebFilter(authenticationManager())
        authenticationWebFilter.setSecurityContextRepository(webSessionContextRepository())
        authenticationWebFilter.setAuthenticationConverter(authenticationConverter())
        authenticationWebFilter.setRequiresAuthenticationMatcher(requiresAuthenticationMatcher())
        return authenticationWebFilter
    }

    @Bean
    fun authenticationManager(): ReactiveAuthenticationManager {
        val udrram = UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService())
        udrram.setPasswordEncoder(passwordEncoder())
        return udrram
    }

    @Bean
    fun authenticationConverter(): ServerFormLoginAuthenticationConverter {
        return ServerFormLoginAuthenticationConverter()
    }

    @Bean
    fun userDetailsService():ReactiveUserDetailsService {
        return UserDetailsServiceImpl()
    }

    @Bean
    fun webSessionContextRepository(): WebSessionServerSecurityContextRepository {
        return WebSessionServerSecurityContextRepository()
    }

    @Bean
    fun requiresAuthenticationMatcher(): ServerWebExchangeMatcher {
        return PathPatternParserServerWebExchangeMatcher("/login", HttpMethod.POST)
    }


}
