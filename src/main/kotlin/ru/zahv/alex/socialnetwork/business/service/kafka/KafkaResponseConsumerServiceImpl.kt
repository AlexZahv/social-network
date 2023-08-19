package ru.zahv.alex.socialnetwork.business.service.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import lombok.extern.slf4j.Slf4j
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Service
import ru.zahv.alex.socialnetwork.business.dto.KafkaPostUpdateDto
import ru.zahv.alex.socialnetwork.business.enums.PostActionType
import ru.zahv.alex.socialnetwork.business.service.cache.PostFeedCacheService
import ru.zahv.alex.socialnetwork.business.service.impl.PostServiceImpl
import ru.zahv.alex.socialnetwork.business.service.ws.StompMessageService
import ru.zahv.alex.socialnetwork.config.WebSocketBrokerConfig.WS_NEW_POST_URL
import ru.zahv.alex.socialnetwork.web.dto.posts.PostResponseDTO
import java.math.BigDecimal

@Slf4j
@Service
class KafkaResponseConsumerServiceImpl(
    val postFeedCacheService: PostFeedCacheService,
    val objectMapper: ObjectMapper,
    val stompMessageService: StompMessageService
) :
    KafkaResponseConsumerService {
    @Override
    @KafkaListener(topics = ["\${kafka.topic.notify-new-post}"], containerFactory = "notifyNewPostConsumerFactory")
    override fun consumeNotifyNewPost(dto: KafkaPostUpdateDto, @Header(KafkaHeaders.OFFSET) offsets: Long) {
        println("KafkaResponseConsumerServiceImpl received dto ${objectMapper.writeValueAsString(dto)}")
        postEventReceive(dto)
    }

    fun postEventReceive(dto: KafkaPostUpdateDto) {
        synchronized(dto.userId.intern()) {
            when (dto.action) {
                PostActionType.CREATE -> postCreatedProcess(dto)
                PostActionType.UPDATE -> postUpdatedProcess(dto)
                PostActionType.DELETE -> postDeletedProcess(dto)
            }
        }
    }

    fun postCreatedProcess(dto: KafkaPostUpdateDto) {
        dto.postResponseDTO?.let { postResponseDTO ->
            stompMessageService.sendMessageSpecificUser(
                WS_NEW_POST_URL,
                dto.userId,
                postResponseDTO
            )
        }

        val cachePostsList =
            postFeedCacheService.getFromCache(
                dto.userId,
                BigDecimal.ZERO,
                BigDecimal(PostServiceImpl.CACHE_LIMIT_ELEMENTS)
            )
        if (cachePostsList.isNotEmpty()) {
            var newFeedList: List<PostResponseDTO> = listOf(dto.postResponseDTO!!)

            newFeedList = newFeedList.plus(
                cachePostsList.dropLast(
                    (cachePostsList.size - PostServiceImpl.CACHE_LIMIT_ELEMENTS.toInt()).coerceAtLeast(
                        0
                    )
                )
            )

            postFeedCacheService.storeCache(dto.userId, newFeedList)
            println("Post create process, after store cache")
        }
    }

    fun postUpdatedProcess(dto: KafkaPostUpdateDto) {
        val cachePostsList =
            postFeedCacheService.getFromCache(
                dto.userId,
                BigDecimal.ZERO,
                BigDecimal(PostServiceImpl.CACHE_LIMIT_ELEMENTS)
            )
                .toMutableList()
        println("Post update process, cache list size ${cachePostsList.size}")
        if (cachePostsList.isNotEmpty()) {
            cachePostsList.forEachIndexed { index, post ->
                post.takeIf { it.id == dto.postResponseDTO!!.id }?.let {
                    cachePostsList[index] = dto.postResponseDTO!!
                }
            }
            postFeedCacheService.storeCache(dto.userId, cachePostsList)
        }
    }

    fun postDeletedProcess(dto: KafkaPostUpdateDto) {
        println("Call post deleted process, ${dto.userId}")
        postFeedCacheService.cleanCache(dto.userId)
    }
}
