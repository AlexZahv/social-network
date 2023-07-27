package ru.zahv.alex.socialnetwork.business.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class PostNotFoundException(message: String?) : BaseSocialNetworkException(message) {
    constructor() : this("Пост не найден")
}
