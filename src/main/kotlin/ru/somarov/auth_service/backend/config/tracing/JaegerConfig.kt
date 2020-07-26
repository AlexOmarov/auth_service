package ru.somarov.auth_service.backend.config.tracing

import io.opentracing.Tracer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 *
 *  @author AlexOmarov
 *  @date 24.07.2020
 *  @version 1.0.0
 *  @since 1.0.0
 */
@Configuration
class JaegerConfig {

    /*@Bean
    fun tracer(): Tracer {
        return io.jaegertracing.Configuration("auth_service").tracerBuilder
                .build()
    }*/
}