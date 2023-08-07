package ru.zahv.alex.socialnetwork.business.service

import ru.zahv.alex.socialnetwork.web.dto.posts.PostResponseDTO

interface PostQueueSenderService {

    fun sendCreatedEvent(userId: String, postResponseDTO: PostResponseDTO)

    fun sendUpdatedEvent(userId: String, postResponseDTO: PostResponseDTO)

    fun sendDeletedEvent(userId: String)
}