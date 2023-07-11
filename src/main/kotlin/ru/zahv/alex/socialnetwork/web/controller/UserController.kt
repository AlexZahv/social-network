package ru.zahv.alex.socialnetwork.web.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.zahv.alex.socialnetwork.aop.Authenticated
import ru.zahv.alex.socialnetwork.business.service.UserService
import ru.zahv.alex.socialnetwork.config.OpenApiConfiguration
import ru.zahv.alex.socialnetwork.web.dto.ErrorResponseDTO
import ru.zahv.alex.socialnetwork.web.dto.UserRegisterRequestDTO
import ru.zahv.alex.socialnetwork.web.dto.UserRegisterResponseDTO
import ru.zahv.alex.socialnetwork.web.dto.UserResponseDTO

@RestController
@RequestMapping("/api/user")
@Tag(name = "user", description = "the user API")
class UserController(private val userService: UserService) {
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
                        schema = Schema(implementation = ErrorResponseDTO::class),
                    ),
                ],
            ),
            ApiResponse(
                responseCode = "503",
                description = "Ошибка сервера",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponseDTO::class),
                    ),
                ],
            ),
        ],
    )
    @Authenticated
    @SecurityRequirement(name = OpenApiConfiguration.BEARER_AUTH_SECURITY_SCHEME_NAME)
    @GetMapping(value = ["/get/{id}"], produces = ["application/json"])
    fun getUserById(
        @Parameter(name = "id", description = "Идентификатор пользователя", required = true)
        @PathVariable("id")
        id: String,
    ): ResponseEntity<UserResponseDTO?>? {
        return ResponseEntity.ok(userService.getUserById(id))
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
                        schema = Schema(implementation = ErrorResponseDTO::class),
                    ),
                ],
            ),
            ApiResponse(
                responseCode = "503",
                description = "Ошибка сервера",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponseDTO::class),
                    ),
                ],
            ),
        ],
    )
    @PostMapping(value = ["/register"], produces = ["application/json"], consumes = ["application/json"])
    fun registerNewUser(
        @Parameter(name = "UserRegisterPostRequest", description = "")
        @RequestBody(required = true)
        @Valid
        userRegisterPostRequest: UserRegisterRequestDTO?,
    ): ResponseEntity<UserRegisterResponseDTO?>? {
        return ResponseEntity.ok(userService.registerUser(userRegisterPostRequest!!))
    }

    /**
     * GET /user/search
     * Поиск анкет
     *
     * @param firstName Условие поиска по имени (required)
     * @param lastName Условие поиска по фамилии (required)
     * @return Успешные поиск пользователя (status code 200)
     * or Невалидные данные (status code 400)
     * or Ошибка сервера (status code 500)
     * or Ошибка сервера (status code 503)
     */
    @Operation(
        operationId = "userSearchGet",
        description = "Поиск анкет",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Успешные поиск пользователя",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = UserResponseDTO::class)),
                    ),
                ],
            ),
            ApiResponse(responseCode = "400", description = "Невалидные данные"),
            ApiResponse(
                responseCode = "500",
                description = "Ошибка сервера",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponseDTO::class),
                    ),
                ],
            ),
            ApiResponse(
                responseCode = "503",
                description = "Ошибка сервера",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponseDTO::class),
                    ),
                ],
            ),
        ],
    )
    @GetMapping(value = ["/search"], produces = ["application/json"])
    @Authenticated
    @SecurityRequirement(name = OpenApiConfiguration.BEARER_AUTH_SECURITY_SCHEME_NAME)
    fun userSearchGet(
        @NotNull
        @Parameter(name = "first_name", description = "Условие поиска по имени", required = true, `in` = ParameterIn.QUERY)
        @RequestParam(value = "first_name", required = true)
        firstName: String,
        @NotNull
        @Parameter(name = "last_name", description = "Условие поиска по фамилии", required = true, `in` = ParameterIn.QUERY)
        @RequestParam(value = "last_name", required = true)
        lastName: String,
    ): ResponseEntity<List<UserResponseDTO>>? {
        return ResponseEntity.ok(userService.searchUsersByFirstNameAndLastName(firstName, lastName))
    }
}
