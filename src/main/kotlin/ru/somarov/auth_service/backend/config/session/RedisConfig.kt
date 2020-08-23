package ru.somarov.auth_service.backend.config.session

import org.springframework.context.annotation.Configuration
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession


@Configuration
@EnableRedisWebSession
class RedisConfig