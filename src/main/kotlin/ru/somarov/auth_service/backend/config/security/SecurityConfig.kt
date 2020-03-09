package ru.somarov.auth_service.backend.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository
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

/*@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity*/
class SecurityConfig/*: WebSecurityConfigurerAdapter()*/ {

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
    @Throws(Exception::class)
    /*override*/ fun configure(http: HttpSecurity) {
        http /*.headers().frameOptions().sameOrigin()
                    .and()*/
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                .antMatchers("/",
                        "/blob/**",
                        "/css/**",
                        "/resources/**",
                        "/images/**",
                        "/img/**",
                        "/rest/**",
                        "/favicon.ico",
                        "/js/**"
                ).permitAll()
                .antMatchers("/profile/**", "/orders", "/order", "/add/**", "/account").authenticated()
                .antMatchers("/admin/**", "/manage/**", "/rest/**").hasAuthority("ADMIN")
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/perform_logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login")
                .permitAll()
                .and()
                .csrf()
                .csrfTokenRepository(HttpSessionCsrfTokenRepository())
    }


    @Bean
    fun httpSessionEventPublisher(): HttpSessionEventPublisher {
        return HttpSessionEventPublisher()
    }


    /**
     * Подключение кастомной реализации UserDetailsService
     */
    @Bean
    /*override*/ fun userDetailsService(): UserDetailsService {
        return UserDetailsServiceImpl()
    }

}