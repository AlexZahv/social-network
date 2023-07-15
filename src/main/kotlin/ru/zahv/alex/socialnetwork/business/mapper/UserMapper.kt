package ru.zahv.alex.socialnetwork.business.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import ru.zahv.alex.socialnetwork.business.persistance.domain.UserEntity
import ru.zahv.alex.socialnetwork.web.dto.UserRegisterRequestDTO
import ru.zahv.alex.socialnetwork.web.dto.UserRegisterResponseDTO
import ru.zahv.alex.socialnetwork.web.dto.UserResponseDTO

@Mapper(componentModel = "spring")
interface UserMapper {
    @Mapping(target = "userId", source = "id")
    fun mapToRegisterResponseDTO(entity: UserEntity): UserRegisterResponseDTO
    fun mapToResponseDTO(entity: UserEntity): UserResponseDTO

    @Mapping(target = "password", ignore = true)
    fun mapToEntityFromDTO(dto: UserRegisterRequestDTO): UserEntity
}
