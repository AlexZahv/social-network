package ru.zahv.alex.socialnetwork.business.service.cache

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import ru.zahv.alex.socialnetwork.web.dto.posts.PostResponseDTO
import java.math.BigDecimal
import java.util.concurrent.TimeUnit


@Service
class PostFeedCacheService(
    @Qualifier("redisPostsTemplate") val redisPostsTemplate: RedisTemplate<String, PostResponseDTO>
) {
    @Value("\${spring.data.redis.ttl-seconds}")
    private var cacheValiditySeconds = POSTS_CACHE_VALIDITY_SECONDS

    companion object {
        const val POSTS_KEY_PREFIX = "POSTS_"
        private const val POSTS_CACHE_VALIDITY_SECONDS = 600L
    }

    fun getFromCache(userId: String, offset: BigDecimal, limit: BigDecimal): List<PostResponseDTO> {
        val key = getPostsKey(userId)
        val cachedItems = redisPostsTemplate.opsForList()
            .range(
                key,
                offset.longValueExact(),
                offset.longValueExact() + limit.longValueExact() - 1
            )?.toList() ?: listOf()

        if (cachedItems.isNotEmpty()) {
            refreshExpirationTime(key)
        }

        return cachedItems
    }

    fun storeCache(userId: String, postsList: List<PostResponseDTO>) {
        try {
            if (postsList.isNotEmpty()) {
                val key = getPostsKey(userId)
                cleanCache(userId)
                val ops = redisPostsTemplate.opsForList()
                ops.rightPushAll(key, postsList)
                refreshExpirationTime(key)
            }
        } catch (e: Exception) {
            println("Exception during store cache ${e.message}")
        }
    }

    fun cleanCache(userId: String) {
        try {
            val key = getPostsKey(userId)
            redisPostsTemplate.delete(key)
        } catch (e: Exception) {
            println("Exception during clean cache ${e.message}")
        }
    }

    fun cleanCache(keyList: List<String>) {
        try {
            redisPostsTemplate.delete(keyList)
        } catch (e: Exception) {
            println("Exception during clean cache ${e.message}")
        }
    }

    private fun refreshExpirationTime(key: String) {
        redisPostsTemplate.expire(key, cacheValiditySeconds, TimeUnit.SECONDS)
    }

    private fun getPostsKey(userId: String): String {
        return "$POSTS_KEY_PREFIX$userId"
    }
}

