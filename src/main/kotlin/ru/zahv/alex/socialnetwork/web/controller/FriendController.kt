package ru.zahv.alex.socialnetwork.web.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.zahv.alex.socialnetwork.aop.Authenticated
import ru.zahv.alex.socialnetwork.business.service.FriendService
import ru.zahv.alex.socialnetwork.config.OpenApiConfiguration
import ru.zahv.alex.socialnetwork.utils.SecurityContextHolder
import ru.zahv.alex.socialnetwork.web.dto.ErrorResponseDTO

@RestController
@RequestMapping("friend")
@Tag(name = "friend", description = "the friend API")
class FriendController(val friendService: FriendService) {

    /**
     * DELETE /delete/{user_id}
     *
     * @param userId  (required)
     * @return Пользователь успешно удалил из друзей пользователя (status code 200)
     * or Невалидные данные ввода (status code 400)
     * or Неавторизованный доступ (status code 401)
     * or Ошибка сервера (status code 500)
     * or Ошибка сервера (status code 503)
     */
    @Operation(
        operationId = "friendDeleteUserIdPut",
        responses = [ApiResponse(
            responseCode = "200",
            description = "Пользователь успешно удалил из друзей пользователя"
        ), ApiResponse(responseCode = "400", description = "Невалидные данные ввода"), ApiResponse(
            responseCode = "401",
            description = "Неавторизованный доступ"
        ), ApiResponse(
            responseCode = "500",
            description = "Ошибка сервера",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = ErrorResponseDTO::class)
            )]
        ), ApiResponse(
            responseCode = "503",
            description = "Ошибка сервера",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = ErrorResponseDTO::class)
            )]
        )]
    )
    @Authenticated
    @SecurityRequirement(name = OpenApiConfiguration.BEARER_AUTH_SECURITY_SCHEME_NAME)
    @DeleteMapping(value = ["/delete/{user_id}"], produces = ["application/json"])
    fun friendDeleteUserIdPut(
        @Parameter(
            name = "user_id",
            description = "Идентификатор пользователя",
            required = true,
            `in` = ParameterIn.PATH
        ) @PathVariable("user_id") userId: String
    ): ResponseEntity<Void?>? {
        friendService.deleteFriendShip(SecurityContextHolder.getCurrentUser(), userId)
        return ResponseEntity.ok().build()
    }


    /**
     * PUT /set/{user_id}
     *
     * @param userId  (required)
     * @return Пользователь успешно указал своего друга (status code 200)
     * or Невалидные данные ввода (status code 400)
     * or Неавторизованный доступ (status code 401)
     * or Ошибка сервера (status code 500)
     * or Ошибка сервера (status code 503)
     */
    @Operation(
        operationId = "friendSetUserIdPut",
        responses = [ApiResponse(
            responseCode = "200",
            description = "Пользователь успешно указал своего друга"
        ), ApiResponse(responseCode = "400", description = "Невалидные данные ввода"), ApiResponse(
            responseCode = "401",
            description = "Неавторизованный доступ"
        ), ApiResponse(
            responseCode = "500",
            description = "Ошибка сервера",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = ErrorResponseDTO::class)
            )]
        ), ApiResponse(
            responseCode = "503",
            description = "Ошибка сервера",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = ErrorResponseDTO::class)
            )]
        )]
    )
    @PutMapping(value = ["/set/{user_id}"], produces = ["application/json"])
    @Authenticated
    @SecurityRequirement(name = OpenApiConfiguration.BEARER_AUTH_SECURITY_SCHEME_NAME)
    fun friendSetUserIdPut(
        @Parameter(
            name = "user_id",
            description = "",
            required = true,
            `in` = ParameterIn.PATH
        ) @PathVariable("user_id") userId: String
    ): ResponseEntity<Void?>? {
        friendService.addFriendShip(SecurityContextHolder.getCurrentUser(), userId)
        return ResponseEntity.ok().build()
    }
}