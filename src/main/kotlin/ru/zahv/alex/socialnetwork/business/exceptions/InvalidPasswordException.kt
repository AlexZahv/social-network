package ru.zahv.alex.socialnetwork.business.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class InvalidPasswordException(message: String?) : BaseSocialNetworkException(message) {
    constructor() : this("Невалидные данные")
}
