package ru.berte.auth.presentation.http

import kotlinx.coroutines.flow.toList
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange
import ru.berte.auth.application.service.CommonService
import ru.berte.auth.presentation.web.dto.Actor
import ru.berte.auth.presentation.web.standard.ResponseMetadata
import ru.berte.auth.presentation.web.standard.ResultCode
import ru.berte.auth.presentation.web.standard.StandardResponse

@RestController
@RequestMapping("/actors")
@Validated
class CommonController(private val service: CommonService) : ISwaggerCommonController {
    private val logger = LoggerFactory.getLogger(CommonController::class.java)

    @GetMapping
    override suspend fun getActors(): StandardResponse<List<Actor>> {
        logger.info("Got getRequestStatuses request")
        val response = service.getActors().toList()
        return StandardResponse(response, ResponseMetadata(ResultCode.OK, ""))
    }

    @PostMapping("/login", produces = [MediaType.APPLICATION_JSON_VALUE])
    override suspend fun login(swe: ServerWebExchange): StandardResponse<String> {
        return StandardResponse("DONE", ResponseMetadata(ResultCode.OK, ""))
    }
}
