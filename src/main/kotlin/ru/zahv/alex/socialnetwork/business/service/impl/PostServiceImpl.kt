package ru.zahv.alex.socialnetwork.business.service.impl

import org.springframework.stereotype.Service
import ru.zahv.alex.socialnetwork.business.exceptions.PostNotFoundException
import ru.zahv.alex.socialnetwork.business.mapper.PostMapper
import ru.zahv.alex.socialnetwork.business.persistance.repository.PostDao
import ru.zahv.alex.socialnetwork.business.service.PostService
import ru.zahv.alex.socialnetwork.web.dto.posts.PostCreateRequestDTO
import ru.zahv.alex.socialnetwork.web.dto.posts.PostResponseDTO
import ru.zahv.alex.socialnetwork.web.dto.posts.PostUpdateRequestDTO
import java.math.BigDecimal

@Service
class PostServiceImpl(val postDao: PostDao, val mapper: PostMapper) : PostService {
    override fun createPost(dto: PostCreateRequestDTO): String {
        return postDao.createPost(dto)
    }

    override fun deletePost(id: String) {
        postDao.deletePost(id)
    }

    override fun getPost(id: String): PostResponseDTO {
        val entity = postDao.getPost(id)
            ?: throw PostNotFoundException()
        return mapper.mapToResponseDTO(entity)
    }

    override fun updatePost(dto: PostUpdateRequestDTO) {
        postDao.updatePost(dto)
    }

    override fun getPostFeed(offset: BigDecimal, limit: BigDecimal): List<PostResponseDTO> {
        return postDao.getPostFeed(offset, limit)
            .asSequence()
            .map { postEntity -> mapper.mapToResponseDTO(postEntity) }
            .toList()
    }
}