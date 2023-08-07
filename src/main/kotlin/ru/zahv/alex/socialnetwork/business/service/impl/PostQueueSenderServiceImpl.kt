package ru.zahv.alex.socialnetwork.business.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import ru.zahv.alex.socialnetwork.business.dto.RedisPostUpdateDto
import ru.zahv.alex.socialnetwork.business.enums.PostActionType
import ru.zahv.alex.socialnetwork.business.enums.RedisTopic
import ru.zahv.alex.socialnetwork.business.service.PostQueueSenderService
import ru.zahv.alex.socialnetwork.web.dto.posts.PostResponseDTO

@Service
class PostQueueSenderServiceImpl(
    @Qualifier("redisPostQueueTemplate") val redisTemplate: StringRedisTemplate,
    private val objectMapper: ObjectMapper
) : PostQueueSenderService {
    override fun sendCreatedEvent(userId: String, postResponseDTO: PostResponseDTO) {
        sendMessage(RedisTopic.POST_UPDATED.code, getStringJson(RedisPostUpdateDto(userId, PostActionType.CREATE, postResponseDTO)))
    }

    override fun sendUpdatedEvent(userId: String, postResponseDTO: PostResponseDTO) {
        sendMessage(RedisTopic.POST_UPDATED.code, getStringJson(RedisPostUpdateDto(userId, PostActionType.UPDATE, postResponseDTO)))
    }

    override fun sendDeletedEvent(userId: String) {
        println("Send deleted event $userId")
        sendMessage(RedisTopic.POST_UPDATED.code, getStringJson(RedisPostUpdateDto(userId, PostActionType.DELETE)))
    }

    private fun sendMessage(topic: String, json: String) {
        redisTemplate.convertAndSend(topic, json)
    }

    private fun getStringJson(dto: RedisPostUpdateDto): String {
        return objectMapper.writeValueAsString(dto)
    }

    private fun <T> getStringJson(dto: T): String {
        return objectMapper.writeValueAsString(dto)
    }
}