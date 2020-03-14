package ru.somarov.auth_service.backend.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import reactor.core.publisher.Mono
import ru.somarov.auth_service.backend.security.GrantedAuthorityImpl
import ru.somarov.auth_service.backend.security.UserDetailsImpl

/**
 *  Имплементация {@link UserDetailsService UserDetailsService} - класс, представляющий собой
 *  сервис аутентификации
 *  пользователей с переопределенным методом получения данных аутентифицированного пользователя.
 *
 *  @author AlexOmarov
 *  @date 24.02.2020
 *  @version 1.0.0
 *  @since 1.0.0
 */
class UserDetailsServiceImpl: ReactiveUserDetailsService {

/*

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

*/

    /**
     * Регистрация новых пользователей
     * @param email email нового пользователя
     * @param password пароль нового пользоватля
     * @param role роль нового пользователя
     * @param name ник нового пользователя
     * @return новый пользователь из базы
     */
/*
    fun registerUser: User(val email: String, val password: String, val role: Role, val name: String) {
        return userRepo.save(
                new User(email,passwordEncoder.encode(password),role, name)
        );
    }
*/



    /**
     * Получение аутентифицированного пользователя
     * @param email email пользователя
     * @return UserDetailsImpl с заполненными данными пользователя
     * @throws UsernameNotFoundException Пользователь не найден
     */
    override fun findByUsername(username: String?): Mono<UserDetails> {
        return Mono.just(UserDetailsImpl("","","",
                false,false,false,
                false, mutableListOf(GrantedAuthorityImpl(""))))
    }
}