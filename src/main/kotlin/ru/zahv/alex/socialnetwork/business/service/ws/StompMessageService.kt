package ru.zahv.alex.socialnetwork.business.service.ws

import ru.zahv.alex.socialnetwork.web.dto.posts.PostResponseDTO

interface StompMessageService {

    fun sendMessageSpecificUser(topic: String, userId: String, message: PostResponseDTO)
}
