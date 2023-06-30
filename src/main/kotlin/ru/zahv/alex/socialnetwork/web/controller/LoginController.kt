package ru.zahv.alex.socialnetwork.web.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import ru.zahv.alex.socialnetwork.web.dto.Error500ResponseDTO
import ru.zahv.alex.socialnetwork.web.dto.LoginRequestDTO
import ru.zahv.alex.socialnetwork.web.dto.LoginResponseDTO

@Tag(name = "login", description = "the login API")
@RestController
class LoginController {
    /**
     * POST /login
     * Упрощенный процесс аутентификации путем передачи идентификатор пользователя и получения токена для дальнейшего прохождения авторизации
     *
     * @param loginRequestDTO (optional)
     * @return Успешная аутентификация (status code 200)
     * or Невалидные данные (status code 400)
     * or Пользователь не найден (status code 404)
     * or Ошибка сервера (status code 500)
     * or Ошибка сервера (status code 503)
     */
    @Operation(
        operationId = "loginPost",
        responses = [
            ApiResponse(responseCode = "200", description = "Успешная аутентификация", content = [Content(mediaType = "application/json", schema = Schema(implementation = LoginResponseDTO::class))]),
            ApiResponse(responseCode = "400", description = "Невалидные данные"),
            ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            ApiResponse(responseCode = "500", description = "Ошибка сервера", content = [Content(mediaType = "application/json", schema = Schema(implementation = Error500ResponseDTO::class))]),
            ApiResponse(responseCode = "503", description = "Ошибка сервера", content = [Content(mediaType = "application/json", schema = Schema(implementation = Error500ResponseDTO::class))]),
        ],
    )
    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/login"],
        produces = ["application/json"],
        consumes = ["application/json"],
    )
    fun loginPost(
        @Parameter(name = "LoginRequestDTO", description = "Данные для авторизации")
        @RequestBody(required = false)
        @Valid
        loginRequestDTO: LoginRequestDTO?,
    ): ResponseEntity<LoginResponseDTO?>? {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }
}
