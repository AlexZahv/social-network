package ru.zahv.alex.socialnetwork.config.kafka

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "kafka.topic")
class KafkaTopicConfig {
    var notifyNewPost: String? = null
}
