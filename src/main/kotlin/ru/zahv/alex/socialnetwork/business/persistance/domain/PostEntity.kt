package ru.zahv.alex.socialnetwork.business.persistance.domain

import java.time.LocalDateTime

data class PostEntity(
    var id: String? = null,
    var text: String? = null,
    var authorId: String? = null,
    var createDate: LocalDateTime? = null
)