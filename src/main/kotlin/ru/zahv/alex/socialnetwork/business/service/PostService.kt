package ru.zahv.alex.socialnetwork.business.service

import ru.zahv.alex.socialnetwork.web.dto.posts.PostCreateRequestDTO
import ru.zahv.alex.socialnetwork.web.dto.posts.PostResponseDTO
import ru.zahv.alex.socialnetwork.web.dto.posts.PostUpdateRequestDTO
import java.math.BigDecimal

/**
 * @author azakhvalinskiy
 * Service contains logic for working with posts
 */
interface PostService {

    /**
     * Create new post
     * @param dto {@link PostCreateRequestDTO}
     */
    fun createPost(dto: PostCreateRequestDTO): String

    /**
     * Delete existing post
     * @param id - post id
     */
    fun deletePost(id: String)

    /**
     * Get post by id
     * @param id - post id
     * @return {@link PostResponseDTO}
     */
    fun getPost(id: String): PostResponseDTO

    /**
     * Update existing post
     * @param dto {@link PostUpdateRequestDTO}
     */
    fun updatePost(dto: PostUpdateRequestDTO)

    /**
     * Get post feed
     * @param limit - limit elements on page
     * @param offset - page offset
     * @return {@link List<PostResponseDTO>} - search result
     */
    fun getPostFeed(offset: BigDecimal, limit: BigDecimal): List<PostResponseDTO>
}