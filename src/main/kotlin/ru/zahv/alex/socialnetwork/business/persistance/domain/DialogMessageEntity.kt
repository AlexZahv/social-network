package ru.zahv.alex.socialnetwork.business.persistance.domain

import java.time.LocalDateTime

data class DialogMessageEntity (
    var id: String? = null,
    var dialogId: String? = null,
    var text: String? = null,
    var fromUserId: String? = null,
    var toUserId: String? = null,
    var createDate: LocalDateTime? = null
)