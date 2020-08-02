package ru.somarov.auth_service.backend.config.tracing

import io.opentracing.Tracer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.web.server.WebFilter
import ru.somarov.auth_service.backend.config.tracing.filter.TracingWebFilter

/**
 *
 *  @author AlexOmarov
 *  @date 24.07.2020
 *  @version 1.0.0
 *  @since 1.0.0
 */
@Configuration
class JaegerConfig {

    /*@Value("\${opentracing.jaeger.udp-sender.host}")
    private lateinit var jaegerHost: String

    @Value("\${opentracing.jaeger.udp-sender.port}")
    private lateinit var jaegerPort: String


    @Bean
    fun tracer(): Tracer {
        return io.jaegertracing.Configuration("auth_service").withSampler(
                io.jaegertracing.Configuration.SamplerConfiguration()
                        .withManagerHostPort("${jaegerHost}:${jaegerPort}")
                        .withType("remote")
        ).tracerBuilder.build()
    }*/

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    fun tracingFilter(tracer: Tracer): WebFilter {
        return TracingWebFilter(tracer)
    }
}