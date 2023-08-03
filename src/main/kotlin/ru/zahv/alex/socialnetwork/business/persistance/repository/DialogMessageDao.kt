package ru.zahv.alex.socialnetwork.business.persistance.repository

import ru.zahv.alex.socialnetwork.business.persistance.domain.DialogMessageEntity

interface DialogMessageDao {
    fun addMessage(entity: DialogMessageEntity): DialogMessageEntity

    fun getAllMessageList(userId: String, currentUserId: String): List<DialogMessageEntity>?
}