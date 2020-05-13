package ru.somarov.auth_service.backend.config.listener.startup

import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Profile
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component
import ru.somarov.auth_service.backend.config.flyway.FlywayConfig


/**
 *
 *  @author AlexOmarov
 *  @date 04.04.2020
 *  @version 1.0.0
 *  @since 1.0.0
 */
@Component
@Profile("!test")
class StartupApplicationListener : ApplicationListener<ContextRefreshedEvent?> {

    @Value("\${spring.flyway.user}")
    private var username: String = ""

    @Value("\${spring.flyway.password}")
    private var password: String = ""

    @Value("\${spring.flyway.url}")
    private var url: String = ""

    companion object {
        private val LOG: Logger = getLogger(StartupApplicationListener::class.java)
    }

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        //FlywayConfig.flyway(url = url,password = password,username = username).migrate()
    }
}