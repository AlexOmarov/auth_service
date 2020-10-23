package ru.somarov.auth_service.backend.config.tracing.filter

import io.opentracing.Span
import io.opentracing.Tracer
import mu.KotlinLogging
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

/**
 *
 *  @author AlexOmarov
 *  @date 30.07.2020
 *  @version 1.0.0
 *  @since 1.0.0
 */
class TracingWebFilter(private val tracer: Tracer) : WebFilter {
    private val log = KotlinLogging.logger {}
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val activeSpan: Span? = tracer.scopeManager().activeSpan()
        log.info { "Web filter for tracing, method name ${exchange.request.path}, span $activeSpan" }
        // TODO: operation name doesn't change. Maybe because of default tracing filter which goes first
        activeSpan?.setOperationName(exchange.request.path.toString())
        activeSpan?.setTag("TRACING WEB FILTER", "DONE")
        exchange.response.headers.add("web-filter", "web-filter-test")
        return chain.filter(exchange)
    }
}