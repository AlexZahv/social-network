package ru.zahv.alex.socialnetwork.business.persistance.repository.plain

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.script.RedisScript
import org.springframework.stereotype.Repository
import ru.zahv.alex.socialnetwork.business.persistance.domain.DialogMessageEntity
import ru.zahv.alex.socialnetwork.business.persistance.repository.DialogMessageDao
import ru.zahv.alex.socialnetwork.utils.getDialogId


@Repository
class LuaDialogMessageDaoImpl(
        val redisLuaTemplate: RedisTemplate<String, String>,
        val redisLuaGetMessageScript: RedisScript<String>,
        val redisLuaAddMessageScript: RedisScript<String>,
) : DialogMessageDao {
    private val objectMapper = ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
            .findAndRegisterModules()

    override fun addMessage(entity: DialogMessageEntity): DialogMessageEntity {
        val keys = listOf(getDialogId(entity.fromUserId!!, entity.toUserId!!))

        val args = arrayOf(objectMapper.writeValueAsString(entity))
        val result = redisLuaTemplate.execute(redisLuaAddMessageScript, keys, *args)
        return objectMapper.readValue(result, DialogMessageEntity::class.java)
    }

    override fun getAllMessageList(userId: String, currentUserId: String): List<DialogMessageEntity>? {
        val keys = listOf(getDialogId(userId, currentUserId))
        val result = redisLuaTemplate.execute(redisLuaGetMessageScript, keys)
        return getMessagesListFromStr(result)
    }


    private fun getMessagesListFromStr(json: String): List<DialogMessageEntity> {
        return try {
            val strings = objectMapper.readValue(json, MutableList::class.java) as List<String>
            strings.asSequence().map { s: String -> objectMapper.readValue(s, DialogMessageEntity::class.java)}.toList()
        } catch (e: JsonProcessingException) {
            emptyList()
        }
    }
}
