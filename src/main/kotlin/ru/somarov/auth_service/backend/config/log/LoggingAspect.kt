package ru.somarov.auth_service.backend.config.log

import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
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
class LoggingAspect {

    private val log = KotlinLogging.logger {}

    @Around("@annotation(Loggable)")
    fun log(joinPoint: ProceedingJoinPoint): Any? {
        val start = currentTimeMillis()
        return when (val result = joinPoint.proceed()) {
            is Mono<*> -> {
                result.doOnSuccess {
                    logOperation(joinPoint, it, start)
                }
            }
            is Flux<*> -> {
                result.doOnEach {
                    logOperation(joinPoint, it.get(), start)
                }.doOnComplete {
                    log.info { "FLUX COMPLETED IN LOGGING" }
                }
            }
            else -> {
                logOperation(joinPoint, result, start)
                result
            }
        }
    }

    private fun logOperation(joinPoint: ProceedingJoinPoint, result: Any?, start: Long) {
        log.info {
            "Enter: ${joinPoint.signature.declaringTypeName}." +
                    "${joinPoint.signature.name}() " +
                    "with argument[s] = ${joinPoint.args}"
        }
        log.info {
            "Exit: ${joinPoint.signature.declaringTypeName}" +
                    ".${joinPoint.signature.name}() " +
                    "had arguments = ${joinPoint.args}, " +
                    "with result = $result, Execution time = ${currentTimeMillis() - start} ms"
        }
    }

}