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
import jakarta.validation.constraints.DecimalMin
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import ru.zahv.alex.socialnetwork.aop.Authenticated
import ru.zahv.alex.socialnetwork.config.OpenApiConfiguration
import ru.zahv.alex.socialnetwork.web.dto.ErrorResponseDTO
import ru.zahv.alex.socialnetwork.web.dto.posts.PostCreateRequestDTO
import ru.zahv.alex.socialnetwork.web.dto.posts.PostResponseDTO
import ru.zahv.alex.socialnetwork.web.dto.posts.PostUpdateRequestDTO
import java.math.BigDecimal

@Validated
@Tag(name = "post", description = "the post API")
@RestController("post")
@RequestMapping
class PostController {
    /**
     * POST /post/create
     *
     * @param postCreatePostRequest  (optional)
     * @return Успешно создан пост (status code 200)
     * or Невалидные данные ввода (status code 400)
     * or Неавторизованный доступ (status code 401)
     * or Ошибка сервера (status code 500)
     * or Ошибка сервера (status code 503)
     */
    @Operation(
        operationId = "postCreatePost",
        responses = [ApiResponse(
            responseCode = "200",
            description = "Успешно создан пост",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = String::class))]
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
    @PostMapping(
        value = ["/create"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    @Authenticated
    @SecurityRequirement(name = OpenApiConfiguration.BEARER_AUTH_SECURITY_SCHEME_NAME)
    fun postCreatePost(
        @Parameter(
            name = "PostCreatePostRequest",
            description = ""
        ) @Valid @RequestBody(required = false) postCreatePostRequest: PostCreateRequestDTO?
    ): ResponseEntity<String?>? {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }


    /**
     * PUT /post/delete/{id}
     *
     * @param id  (required)
     * @return Успешно удален пост (status code 200)
     * or Невалидные данные ввода (status code 400)
     * or Неавторизованный доступ (status code 401)
     * or Ошибка сервера (status code 500)
     * or Ошибка сервера (status code 503)
     */
    @Operation(
        operationId = "postDeleteIdPut",
        responses = [ApiResponse(
            responseCode = "200",
            description = "Успешно удален пост"
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
    @PutMapping(value = ["/delete/{id}"], produces = ["application/json"])
    @Authenticated
    @SecurityRequirement(name = OpenApiConfiguration.BEARER_AUTH_SECURITY_SCHEME_NAME)
    fun postDeleteIdPut(
        @Parameter(
            name = "id",
            description = "",
            required = true,
            `in` = ParameterIn.PATH
        ) @PathVariable("id") id: String?
    ): ResponseEntity<Void?>? {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }


    /**
     * GET /post/feed
     *
     * @param offset  (optional, default to 0)
     * @param limit  (optional, default to 10)
     * @return Успешно получены посты друзей (status code 200)
     * or Невалидные данные ввода (status code 400)
     * or Неавторизованный доступ (status code 401)
     * or Ошибка сервера (status code 500)
     * or Ошибка сервера (status code 503)
     */
    @Operation(
        operationId = "postFeedGet",
        responses = [ApiResponse(
            responseCode = "200",
            description = "Успешно получены посты друзей",
            content = [Content(
                mediaType = "application/json",
                array = ArraySchema(schema = Schema(implementation = PostResponseDTO::class))
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
        )]
    )
    @GetMapping(value = ["/feed"], produces = ["application/json"])
    @Authenticated
    @SecurityRequirement(name = OpenApiConfiguration.BEARER_AUTH_SECURITY_SCHEME_NAME)
    fun postFeedGet(
        @DecimalMin("0") @Parameter(name = "offset", description = "", `in` = ParameterIn.QUERY) @Valid @RequestParam(
            value = "offset",
            required = false,
            defaultValue = "0"
        ) offset: BigDecimal?,
        @DecimalMin("1") @Parameter(name = "limit", description = "", `in` = ParameterIn.QUERY) @Valid @RequestParam(
            value = "limit",
            required = false,
            defaultValue = "10"
        ) limit: BigDecimal?
    ): ResponseEntity<List<PostResponseDTO?>> {
        return ResponseEntity<List<PostResponseDTO?>>(HttpStatus.NOT_IMPLEMENTED)
    }


    /**
     * GET /post/get/{id}
     *
     * @param id  (required)
     * @return Успешно получен пост (status code 200)
     * or Невалидные данные ввода (status code 400)
     * or Неавторизованный доступ (status code 401)
     * or Ошибка сервера (status code 500)
     * or Ошибка сервера (status code 503)
     */
    @Operation(
        operationId = "postGetIdGet",
        responses = [ApiResponse(
            responseCode = "200",
            description = "Успешно получен пост",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = PostResponseDTO::class)
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
        )]
    )
    @GetMapping(value = ["/get/{id}"], produces = ["application/json"])
    @Authenticated
    @SecurityRequirement(name = OpenApiConfiguration.BEARER_AUTH_SECURITY_SCHEME_NAME)
    fun postGetIdGet(
        @Parameter(
            name = "id",
            description = "",
            required = true,
            `in` = ParameterIn.PATH
        ) @PathVariable("id") id: String?
    ): ResponseEntity<PostResponseDTO?> {
        return ResponseEntity<PostResponseDTO?>(HttpStatus.NOT_IMPLEMENTED)
    }


    /**
     * PUT /post/update
     *
     * @param postUpdatePutRequest  (optional)
     * @return Успешно изменен пост (status code 200)
     * or Невалидные данные ввода (status code 400)
     * or Неавторизованный доступ (status code 401)
     * or Ошибка сервера (status code 500)
     * or Ошибка сервера (status code 503)
     */
    @Operation(
        operationId = "postUpdatePut",
        responses = [ApiResponse(
            responseCode = "200",
            description = "Успешно изменен пост"
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
    @PutMapping(
        value = ["/update"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    @Authenticated
    @SecurityRequirement(name = OpenApiConfiguration.BEARER_AUTH_SECURITY_SCHEME_NAME)
    fun postUpdatePut(
        @Parameter(
            name = "PostUpdatePutRequest",
            description = ""
        ) @Valid @RequestBody(required = false) postUpdatePutRequest: PostUpdateRequestDTO?
    ): ResponseEntity<Void?>? {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }
}