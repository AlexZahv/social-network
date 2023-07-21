package ru.zahv.alex.socialnetwork.business.persistance.domain

import java.time.LocalDateTime

data class AuthTokenEntity(
    var id: String? = null,
    var issueDate: LocalDateTime? = null,
    var expireDate: LocalDateTime? = null,
    var userId: String? = null,
)
