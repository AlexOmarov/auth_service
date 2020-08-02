package ru.somarov.auth_service.backend.config.tracing

import io.opentracing.Tracer
import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.lang.System.currentTimeMillis


/**
 *
 *  @author AlexOmarov
 *  @date 26.07.2020
 *  @version 1.0.0
 *  @since 1.0.0
 */
@Aspect
@Component
class TracingAspect {

    @Qualifier("tracer")
    @Autowired
    private lateinit var tracer: Tracer

    private val log = KotlinLogging.logger {}

    @Around("@annotation(Traceable)")
    fun trace(joinPoint: ProceedingJoinPoint): Any? {
        tracer.scopeManager().activeSpan().setOperationName("NEW OP NAME")
        tracer.activateSpan(tracer.buildSpan(joinPoint.signature.toShortString()).start()).use {
            val activeSpan = tracer.scopeManager().activeSpan()
            activeSpan.setTag("method.args", joinPoint.args.toString())
            return when (val result = joinPoint.proceed()) {
                is Mono<*> -> {
                    result.doOnSuccess {
                        activeSpan.log(mapOf("event" to joinPoint.signature.name, "type" to "Mono", "value" to result.toString()))
                        activeSpan.finish()
                    }
                }
                is Flux<*> -> {
                    result.doOnEach {
                        activeSpan.log(mapOf("event" to joinPoint.signature.name, "type" to "Flux", "value" to it.toString()))
                    }.doOnComplete {
                        log.info { "FLUX COMPLETED IN TRACING" }
                        activeSpan.finish()
                    }
                }
                else -> {
                    activeSpan.log(mapOf("event" to joinPoint.signature.name, "type" to result.javaClass.name, "value" to result.toString()))
                    activeSpan.finish()
                }
            }
        }
    }

}