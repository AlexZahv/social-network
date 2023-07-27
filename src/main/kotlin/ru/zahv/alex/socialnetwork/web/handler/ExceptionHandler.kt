package ru.zahv.alex.socialnetwork.web.handler

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import ru.zahv.alex.socialnetwork.business.exceptions.*
import ru.zahv.alex.socialnetwork.web.dto.ErrorResponseDTO

@ControllerAdvice
class ExceptionHandler(val objectMapper: ObjectMapper) : ResponseEntityExceptionHandler() {
    @ExceptionHandler(BaseSocialNetworkException::class, CustomAuthorizationException::class)
    fun handleException(ex: RuntimeException, request: WebRequest): ResponseEntity<Any>? {
        val bodyOfResponse = getJSONErrorString(ex.message)
        var status = HttpStatus.INTERNAL_SERVER_ERROR
        when (ex) {
            is CustomAuthorizationException -> {
                status = HttpStatus.UNAUTHORIZED
            }

            is UserNotFoundException -> {
                status = HttpStatus.NOT_FOUND
            }

            is PostNotFoundException -> {
                status = HttpStatus.NOT_FOUND
            }

            is InvalidPasswordException -> {
                status = HttpStatus.BAD_REQUEST
            }
        }
        return handleExceptionInternal(
            ex,
            bodyOfResponse,
            HttpHeaders(),
            status,
            request,
        )
    }

    private fun getJSONErrorString(message: String?): String {
        return objectMapper.writeValueAsString(ErrorResponseDTO(message))
    }
}
