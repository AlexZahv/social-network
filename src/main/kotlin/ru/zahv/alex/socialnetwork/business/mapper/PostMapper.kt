package ru.zahv.alex.socialnetwork.business.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import ru.zahv.alex.socialnetwork.business.persistance.domain.PostEntity
import ru.zahv.alex.socialnetwork.web.dto.posts.PostResponseDTO

@Mapper(componentModel = "spring")
interface PostMapper {
    @Mapping(target = "authorUserId", source = "authorId")
    fun mapToResponseDTO(entity: PostEntity): PostResponseDTO
}