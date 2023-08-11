package ru.zahv.alex.socialnetwork.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.listener.PatternTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter
import ru.zahv.alex.socialnetwork.business.enums.RedisTopic
import ru.zahv.alex.socialnetwork.business.queue.RedisPostUpdatesListener
import java.util.concurrent.Executors

@Configuration
class RedisQueueConfiguration {

    @Bean
    fun container(
        connectionFactory: RedisConnectionFactory?,
        redisPostUpdatesListener: RedisPostUpdatesListener?,
    ): RedisMessageListenerContainer? {
        val container = RedisMessageListenerContainer()
        container.setConnectionFactory(connectionFactory!!)
        container.addMessageListener(
            postUpdateListenerAdapter(redisPostUpdatesListener)!!,
            PatternTopic(RedisTopic.POST_UPDATED.code)
        )
        container.setTaskExecutor(Executors.newSingleThreadExecutor())
        return container
    }

    @Bean
    fun postUpdateListenerAdapter(RedisPostUpdatesListener: RedisPostUpdatesListener?): MessageListenerAdapter? {
        return MessageListenerAdapter(RedisPostUpdatesListener!!, "postEventReceive")
    }

    @Bean
    fun redisPostQueueTemplate(connectionFactory: RedisConnectionFactory?): StringRedisTemplate {
        return StringRedisTemplate(connectionFactory!!)
    }

}