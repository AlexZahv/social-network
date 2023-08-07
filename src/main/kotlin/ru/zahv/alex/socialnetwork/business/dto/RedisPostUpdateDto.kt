package ru.zahv.alex.socialnetwork.business.dto

import ru.zahv.alex.socialnetwork.business.enums.PostActionType
import ru.zahv.alex.socialnetwork.web.dto.posts.PostResponseDTO

data class RedisPostUpdateDto(
    val userId: String,
    val action: PostActionType,
    var postResponseDTO: PostResponseDTO? = null,
) : java.io.Serializable