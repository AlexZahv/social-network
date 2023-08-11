package ru.zahv.alex.socialnetwork.config

import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
import org.springframework.cache.annotation.CachingConfigurer
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import ru.zahv.alex.socialnetwork.web.dto.posts.PostResponseDTO


@Configuration
@EnableCaching
@ImportAutoConfiguration(classes = [CacheAutoConfiguration::class, RedisAutoConfiguration::class])
class RedisConfiguration : CachingConfigurerSupport(), CachingConfigurer {

    @Bean
    fun redisPostsTemplate(connectionFactory: RedisConnectionFactory?): RedisTemplate<String, PostResponseDTO> {
        val template = RedisTemplate<String, PostResponseDTO>()
        template.connectionFactory = connectionFactory

        return template
    }
}

