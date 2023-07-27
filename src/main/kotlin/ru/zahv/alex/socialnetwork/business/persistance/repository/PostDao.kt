package ru.zahv.alex.socialnetwork.business.persistance.repository

import ru.zahv.alex.socialnetwork.business.persistance.domain.PostEntity
import ru.zahv.alex.socialnetwork.web.dto.posts.PostCreateRequestDTO
import ru.zahv.alex.socialnetwork.web.dto.posts.PostUpdateRequestDTO
import java.math.BigDecimal

interface PostDao {
    fun updatePost(postUpdateRequestDTO: PostUpdateRequestDTO)
    fun createPost(createRequestDTO: PostCreateRequestDTO): String
    fun deletePost(id: String)
    fun getPost(id: String): PostEntity?
    fun getPostFeed(offset: BigDecimal, limit: BigDecimal): List<PostEntity>
}