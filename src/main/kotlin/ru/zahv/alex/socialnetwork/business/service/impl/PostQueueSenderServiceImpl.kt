package ru.zahv.alex.socialnetwork.business.service.impl

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import ru.zahv.alex.socialnetwork.business.dto.KafkaPostUpdateDto
import ru.zahv.alex.socialnetwork.business.enums.PostActionType
import ru.zahv.alex.socialnetwork.business.service.PostQueueSenderService
import ru.zahv.alex.socialnetwork.web.dto.posts.PostResponseDTO

@Service
class PostQueueSenderServiceImpl(
    val kafkaTemplate: KafkaTemplate<String, KafkaPostUpdateDto>
) : PostQueueSenderService {
    override fun sendCreatedEvent(userId: String, postResponseDTO: PostResponseDTO) {
        sendMessage(
            KafkaPostUpdateDto(userId, PostActionType.CREATE, postResponseDTO)
        )
    }

    override fun sendUpdatedEvent(userId: String, postResponseDTO: PostResponseDTO) {
        sendMessage(
            KafkaPostUpdateDto(userId, PostActionType.UPDATE, postResponseDTO)
        )
    }

    override fun sendDeletedEvent(userId: String) {
        println("Send deleted event $userId")
        sendMessage(KafkaPostUpdateDto(userId, PostActionType.DELETE))
    }

    private fun sendMessage(dto: KafkaPostUpdateDto) {
        kafkaTemplate.sendDefault(dto)
    }
}
