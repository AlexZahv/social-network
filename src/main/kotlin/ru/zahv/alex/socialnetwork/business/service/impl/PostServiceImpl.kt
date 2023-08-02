package ru.zahv.alex.socialnetwork.business.service.impl

import org.springframework.stereotype.Service
import ru.zahv.alex.socialnetwork.business.exceptions.PostNotFoundException
import ru.zahv.alex.socialnetwork.business.mapper.PostMapper
import ru.zahv.alex.socialnetwork.business.persistance.repository.PostDao
import ru.zahv.alex.socialnetwork.business.service.PostService
import ru.zahv.alex.socialnetwork.business.service.cache.PostFeedCacheService
import ru.zahv.alex.socialnetwork.utils.SecurityContextHolder.Companion.getCurrentUser
import ru.zahv.alex.socialnetwork.web.dto.posts.PostCreateRequestDTO
import ru.zahv.alex.socialnetwork.web.dto.posts.PostResponseDTO
import ru.zahv.alex.socialnetwork.web.dto.posts.PostUpdateRequestDTO
import java.math.BigDecimal

@Service
class PostServiceImpl(
    val postDao: PostDao,
    val mapper: PostMapper,
    val postFeedCacheService: PostFeedCacheService
) : PostService {

    companion object {
        private const val CACHE_LIMIT_ELEMENTS = 1000L
    }

    /**
     * Create post
     * Invalidate redis cache for all recently loggedIn friends of current user, which will force refresh cache
     * @param dto {@link PostCreateRequestDTO}
     */
    override fun createPost(dto: PostCreateRequestDTO): String {
        val result = postDao.createPost(dto)
        postFeedCacheService.cleanCache(postDao.getAllCacheKeyList(getCurrentUser()))
        return result
    }

    /**
     * Delete post
     * Invalidate redis cache for all recently loggedIn friends of current user, which will force refresh cache
     * @param {@link String} - post id
     */
    override fun deletePost(id: String) {
        postDao.deletePost(id)
        postFeedCacheService.cleanCache(postDao.getAllCacheKeyList(getCurrentUser()))
    }

    /**
     * Get post by id
     * @param {@link String} - post id
     */
    override fun getPost(id: String): PostResponseDTO {
        val entity = postDao.getPost(id)
            ?: throw PostNotFoundException()
        return mapper.mapToResponseDTO(entity)
    }

    /**
     * Update post
     * Invalidate redis cache for all recently loggedIn friends of current user, which will force refresh cache
     * @param dto - {@link PostUpdateRequestDTO} update post model
     */
    override fun updatePost(dto: PostUpdateRequestDTO) {
        postDao.updatePost(dto)
        postFeedCacheService.cleanCache(postDao.getAllCacheKeyList(getCurrentUser()))
    }

    /**
     * Get post feed for current user. If offset+ limit is in range(0, 1000), then will try to get records
     * from redis cache, else records will be retrieved from database
     * @param offset - offset for posts feed
     * @param limit - records retrieve limit
     * @return {@link List<PostResponseDTO>}
     */
    override fun getPostFeed(offset: BigDecimal, limit: BigDecimal): List<PostResponseDTO> {
        val currentUserId = getCurrentUser()
        return if (offset.longValueExact() + limit.longValueExact() <= CACHE_LIMIT_ELEMENTS) {
            try {
                getPostFeedFromCache(currentUserId, offset, limit)
            } catch (e: Exception) {
                println("Exception during try to get posts feed from cache, userId $currentUserId, offset $offset, limit $limit")
                getPostsFeedFromDB(currentUserId, offset, limit)
            }
        } else {

            getPostsFeedFromDB(currentUserId, offset, limit)

        }
    }

    /**
     * Try to get records from cache. If result list is empty, then will try to get records from database
     * and store them in redis cache. After that will perform second attempt to get records from cache
     * @param currentUserId - current user id from token
     * @param offset - offset for posts feed
     * @param limit - records retrieve limit
     * @return {@link List<PostResponseDTO>}
     */
    private fun getPostFeedFromCache(
        currentUserId: String,
        offset: BigDecimal,
        limit: BigDecimal
    ): List<PostResponseDTO> {
        var cachedPosts = postFeedCacheService.getFromCache(currentUserId, offset, limit)
        if (cachedPosts.isEmpty()) {
            val persistedPosts =
                getPostsFeedFromDB(currentUserId, BigDecimal.ZERO, BigDecimal.valueOf(CACHE_LIMIT_ELEMENTS))
            postFeedCacheService.storeCache(currentUserId, persistedPosts)
            cachedPosts = postFeedCacheService.getFromCache(currentUserId, offset, limit)
        }

        return cachedPosts
    }

    private fun getPostsFeedFromDB(
        currentUserId: String,
        offset: BigDecimal,
        limit: BigDecimal
    ): List<PostResponseDTO> {
        return postDao.getPostFeed(currentUserId, offset, limit)
            .asSequence()
            .map { postEntity -> mapper.mapToResponseDTO(postEntity) }
            .toList()
    }
}