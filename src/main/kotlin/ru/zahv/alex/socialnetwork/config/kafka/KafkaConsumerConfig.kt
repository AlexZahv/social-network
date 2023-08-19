package ru.zahv.alex.socialnetwork.config.kafka

import lombok.extern.slf4j.Slf4j
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.LongDeserializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.CommonLoggingErrorHandler
import org.springframework.kafka.support.converter.StringJsonMessageConverter
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import ru.zahv.alex.socialnetwork.business.dto.KafkaPostUpdateDto

@Configuration
@EnableKafka
@Slf4j
class KafkaConsumerConfig {
    companion object {
        private const val MAX_POLL_RECORDS = "1"
        private const val MAX_POLL_INTERVAL = "1200000"
    }

    @Value("\${kafka.server}")
    private val server: String? = null

    @Value("\${kafka.group-id}")
    private val consumerGroupId: String? = null

    /**
     * Consumer config
     *
     * @return `Map<String, Object>`
     */
    @Bean
    fun consumerConfig(): Map<String, Any?> {
        val config: MutableMap<String, Any?> = HashMap()
        config[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = server
        config[ConsumerConfig.GROUP_ID_CONFIG] = consumerGroupId
        config[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = true
        config[ConsumerConfig.MAX_POLL_RECORDS_CONFIG] = MAX_POLL_RECORDS
        config[ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG] = MAX_POLL_INTERVAL
        config[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "latest"
        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = ErrorHandlingDeserializer::class.java
        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = ErrorHandlingDeserializer::class.java
        config[ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS] = LongDeserializer::class.java
        config[ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS] = StringDeserializer::class.java
        return config
    }

    /**
     * Error handler
     *
     * @return [CommonLoggingErrorHandler]
     */
    @Bean
    fun errorHandler(): CommonLoggingErrorHandler {
        return CommonLoggingErrorHandler()
    }

    /**
     * Kafka listener container factory for topic notifyNewPost
     *
     * @return [KafkaListenerContainerFactory]
     */
    @Bean
    fun notifyNewPostConsumerFactory(): KafkaListenerContainerFactory<*> {
        return getDefaultListenerContainerFactory<String, KafkaPostUpdateDto>()
    }

    fun <K, V> getDefaultListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<K, V> {
        val factory = ConcurrentKafkaListenerContainerFactory<K, V>()
        factory.consumerFactory = DefaultKafkaConsumerFactory(consumerConfig())
        factory.isBatchListener = false
        factory.setRecordMessageConverter(StringJsonMessageConverter())
        factory.setCommonErrorHandler(errorHandler())
        factory.setConcurrency(10)
        return factory
    }

}
