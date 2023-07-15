package ru.zahv.alex.socialnetwork.business.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
class CustomAuthorizationException(message: String?) : RuntimeException(message)
