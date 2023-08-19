package ru.zahv.alex.socialnetwork.business.service.ws

import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import ru.zahv.alex.socialnetwork.config.WebSocketBrokerConfig.WS_NEW_POST_URL
import ru.zahv.alex.socialnetwork.web.dto.posts.PostResponseDTO

@Service
class StompMessageServiceImpl(val template: SimpMessagingTemplate) : StompMessageService {

    override fun sendMessageSpecificUser(topic: String, userId: String, message: PostResponseDTO) {
        template.convertAndSendToUser(userId, WS_NEW_POST_URL, message)
    }
}
