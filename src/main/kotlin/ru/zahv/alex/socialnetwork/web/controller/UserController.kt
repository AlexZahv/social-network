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
import org.springframework.web.bind.annotation.*
import ru.zahv.alex.socialnetwork.web.dto.Error500ResponseDTO
import ru.zahv.alex.socialnetwork.web.dto.UserRegisterRequestDTO
import ru.zahv.alex.socialnetwork.web.dto.UserRegisterResponseDTO
import ru.zahv.alex.socialnetwork.web.dto.UserResponseDTO

@RestController
@RequestMapping("/api/user")
@Tag(name = "user", description = "the user API")
class UserController {
    /**
     * GET /user/get/{id}
     * Получение анкеты пользователя
     *
     * @param id Идентификатор пользователя (required)
     * @return Успешное получение анкеты пользователя (status code 200)
     * or Невалидные данные (status code 400)
     * or Анкета не найдена (status code 404)
     * or Ошибка сервера (status code 500)
     * or Ошибка сервера (status code 503)
     */
    @Operation(
            operationId = "userGetIdGet",
            responses = [
                ApiResponse(
                        responseCode = "200",
                        description = "Успешное получение анкеты пользователя",
                        content = [
                            Content(mediaType = "application/json", schema = Schema(implementation = UserResponseDTO::class)),
                        ],
                ),
                ApiResponse(responseCode = "400", description = "Невалидные данные"),
                ApiResponse(responseCode = "404", description = "Анкета не найдена"),
                ApiResponse(
                        responseCode = "500",
                        description = "Ошибка сервера",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = Error500ResponseDTO::class),
                            ),
                        ],
                ),
                ApiResponse(
                        responseCode = "503",
                        description = "Ошибка сервера",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = Error500ResponseDTO::class),
                            ),
                        ],
                ),
            ],
    )
    @GetMapping(value = ["/get/{id}"], produces = ["application/json"])
    fun getUserById(
            @Parameter(name = "id", description = "Идентификатор пользователя", required = true)
            @PathVariable("id")
            id: String?,
    ): ResponseEntity<UserResponseDTO?>? {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    /**
     * POST /user/register
     * Регистрация нового пользователя
     *
     * @param userRegisterPostRequest (optional)
     * @return Успешная регистрация (status code 200)
     * or Невалидные данные (status code 400)
     * or Ошибка сервера (status code 500)
     * or Ошибка сервера (status code 503)
     */
    @Operation(
            operationId = "userRegisterPost",
            responses = [
                ApiResponse(
                        responseCode = "200",
                        description = "Успешная регистрация",
                        content = [Content(mediaType = "application/json", schema = Schema(implementation = UserRegisterResponseDTO::class))],
                ),
                ApiResponse(responseCode = "400", description = "Невалидные данные"),
                ApiResponse(
                        responseCode = "500",
                        description = "Ошибка сервера",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = Error500ResponseDTO::class),
                            ),
                        ],
                ),
                ApiResponse(
                        responseCode = "503",
                        description = "Ошибка сервера",
                        content = [Content(mediaType = "application/json",
                                schema = Schema(implementation = Error500ResponseDTO::class))],
                ),
            ],
    )
    @PostMapping(value = ["/user/register"], produces = ["application/json"], consumes = ["application/json"])
    fun registerNewUser(
            @Parameter(name = "UserRegisterPostRequest", description = "")
            @RequestBody(required = false)
            @Valid
            userRegisterPostRequest: UserRegisterRequestDTO?,
    ): ResponseEntity<UserRegisterResponseDTO?>? {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }
}
