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
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import ru.zahv.alex.socialnetwork.aop.Authenticated
import ru.zahv.alex.socialnetwork.business.service.DialogService
import ru.zahv.alex.socialnetwork.config.OpenApiConfiguration
import ru.zahv.alex.socialnetwork.web.dto.ErrorResponseDTO
import ru.zahv.alex.socialnetwork.web.dto.dialogs.DialogMessageRequestDTO
import ru.zahv.alex.socialnetwork.web.dto.dialogs.DialogMessageResponseDTO
import java.util.*

@Validated
@Tag(name = "dialog", description = "the dialog API")
@RestController
@RequestMapping("/api/dialog")
@SecurityRequirement(name = OpenApiConfiguration.BEARER_AUTH_SECURITY_SCHEME_NAME)
class DialogController(private val dialogService: DialogService) {
    /**
     * GET /dialog/{user_id}
     *
     * @param userId  (required)
     * @return Диалог между двумя пользователями (status code 200)
     * or Невалидные данные ввода (status code 400)
     * or Неавторизованный доступ (status code 401)
     * or Ошибка сервера (status code 500)
     * or Ошибка сервера (status code 503)
     */
    @Operation(
        operationId = "dialogUserIdListGet",
        responses = [ApiResponse(
            responseCode = "200",
            description = "Диалог между двумя пользователями",
            content = [Content(
                mediaType = "application/json",
                array = ArraySchema(schema = Schema(implementation = DialogMessageRequestDTO::class))
            )]
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
        )],
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    @Authenticated
    @GetMapping(value = ["/{user_id}"], produces = ["application/json"])
    fun dialogUserIdListGet(
        @Parameter(
            name = "user_id",
            description = "",
            required = true,
            `in` = ParameterIn.PATH
        ) @PathVariable("user_id") userId: String
    ): ResponseEntity<List<DialogMessageResponseDTO>> {

        return ResponseEntity.ok(dialogService.getAllMessageList(userId))
    }

    /**
     * POST /dialog/send
     *
     * @param dto  (optional)
     * @return Успешно отправлено сообщение (status code 200)
     * or Невалидные данные ввода (status code 400)
     * or Неавторизованный доступ (status code 401)
     * or Ошибка сервера (status code 500)
     * or Ошибка сервера (status code 503)
     */
    @Operation(
        operationId = "dialogUserIdSendPost",
        responses = [ApiResponse(responseCode = "200", description = "Успешно отправлено сообщение"), ApiResponse(
            responseCode = "400",
            description = "Невалидные данные ввода"
        ), ApiResponse(responseCode = "401", description = "Неавторизованный доступ"), ApiResponse(
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
    @PostMapping(
        value = ["/send"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun dialogUserIdSendPost(
        @Parameter(
            name = "Dto",
            description = ""
        ) @Valid @RequestBody(required = false) dto: DialogMessageRequestDTO
    ): ResponseEntity<Void> {
        dialogService.addMessage(dto)
        return ResponseEntity.ok().build()
    }
}