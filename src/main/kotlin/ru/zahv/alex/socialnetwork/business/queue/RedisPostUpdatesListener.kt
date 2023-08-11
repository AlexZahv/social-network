package ru.zahv.alex.socialnetwork.business.queue

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import ru.zahv.alex.socialnetwork.business.dto.RedisPostUpdateDto
import ru.zahv.alex.socialnetwork.business.enums.PostActionType
import ru.zahv.alex.socialnetwork.business.service.cache.PostFeedCacheService
import ru.zahv.alex.socialnetwork.business.service.impl.PostServiceImpl.Companion.CACHE_LIMIT_ELEMENTS
import ru.zahv.alex.socialnetwork.web.dto.posts.PostResponseDTO
import java.math.BigDecimal

@Component
class RedisPostUpdatesListener(val objectMapper: ObjectMapper, val postFeedCacheService: PostFeedCacheService) {

    @SuppressWarnings("unused")
    fun postEventReceive(json: String) {
        println("Received event $json")
        val dto = objectMapper.readValue(json, RedisPostUpdateDto::class.java)
        synchronized(dto.userId.intern()) {
            println("Synchronized event $json")

            when (dto.action) {
                PostActionType.CREATE -> postCreatedProcess(dto)
                PostActionType.UPDATE -> postUpdatedProcess(dto)
                PostActionType.DELETE -> postDeletedProcess(dto)
            }
        }
    }

    fun postCreatedProcess(dto: RedisPostUpdateDto) {
        val cachePostsList =
            postFeedCacheService.getFromCache(dto.userId, BigDecimal.ZERO, BigDecimal(CACHE_LIMIT_ELEMENTS))
        if (cachePostsList.isNotEmpty()) {
            var newFeedList: List<PostResponseDTO> = listOf(dto.postResponseDTO!!)

            newFeedList = newFeedList.plus(
                cachePostsList.dropLast((cachePostsList.size - CACHE_LIMIT_ELEMENTS.toInt()).coerceAtLeast(0))
            )

            postFeedCacheService.storeCache(dto.userId, newFeedList)
            println("Post create process, after store cache")
        }
    }

    fun postUpdatedProcess(dto: RedisPostUpdateDto) {
        val cachePostsList =
            postFeedCacheService.getFromCache(dto.userId, BigDecimal.ZERO, BigDecimal(CACHE_LIMIT_ELEMENTS))
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

    fun postDeletedProcess(dto: RedisPostUpdateDto) {
        println("Call post deleted process, ${dto.userId}")
        postFeedCacheService.cleanCache(dto.userId)
    }
}