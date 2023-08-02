package ru.zahv.alex.socialnetwork.business.cache

import org.springframework.data.redis.core.RedisHash

@RedisHash(value = "PrintFormProcess", timeToLive = 14400)
class PostFeedCache {
}