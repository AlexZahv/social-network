package ru.zahv.alex.socialnetwork.business.service.kafka

import ru.zahv.alex.socialnetwork.business.dto.KafkaPostUpdateDto

interface KafkaResponseConsumerService {

    fun consumeNotifyNewPost(dto: KafkaPostUpdateDto, offsets: Long)
}
