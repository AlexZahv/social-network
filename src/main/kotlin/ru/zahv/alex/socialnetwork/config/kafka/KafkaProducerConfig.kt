package ru.zahv.alex.socialnetwork.config.kafka

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.converter.StringJsonMessageConverter
import org.springframework.kafka.support.serializer.JsonSerializer
import ru.zahv.alex.socialnetwork.business.dto.KafkaPostUpdateDto

@Configuration
class KafkaProducerConfig(val topicProducerConfig: KafkaTopicConfig) {

    @Value("\${kafka.server}")
    private val kafkaServer: String? = null

    /**
     * Kafka producer config
     *
     * @return map properties `Map<String, Object>`
     */
    @Bean
    fun producerConfig(): Map<String, Any?> {
        val props: MutableMap<String, Any?> = HashMap()
        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaServer
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
        return props
    }

    /**
     * Kafka template for topic notifyNewPost
     *
     * @return template `KafkaTemplate<String,RedisPostUpdateDto>`
     */
    @get:Bean
    val notifyNewPostTemplate: KafkaTemplate<String, KafkaPostUpdateDto>
        get() {
            val template = getDefaultKafkaDeliveryTemplate<String, KafkaPostUpdateDto>()
            template.defaultTopic = topicProducerConfig.notifyNewPost!!
            return template
        }

    private fun <K, V> getDefaultKafkaDeliveryTemplate(): KafkaTemplate<K, V> {
        val template = KafkaTemplate(
            DefaultKafkaProducerFactory<K, V>(producerConfig())
        )
        template.messageConverter = StringJsonMessageConverter()
        return template
    }
}
