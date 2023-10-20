package ru.berte.auth.presentation.http

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.server.ServerWebExchange
import ru.berte.auth.presentation.web.dto.Actor
import ru.berte.auth.presentation.web.standard.StandardResponse

interface ISwaggerCommonController {

    @Operation(summary = "Gets all actors", description = "Returns 200 if successful")
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "Successful Operation"
        )]
    )
    suspend fun getActors(): StandardResponse<List<Actor>>

    @Operation(summary = "Login", description = "Returns 200 if successful")
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "Successful Operation"
        )]
    )
    suspend fun login(swe: ServerWebExchange): StandardResponse<String>
}
