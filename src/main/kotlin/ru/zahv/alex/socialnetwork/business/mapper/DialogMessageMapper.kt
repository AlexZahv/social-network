package ru.zahv.alex.socialnetwork.business.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import ru.zahv.alex.socialnetwork.business.persistance.domain.DialogMessageEntity
import ru.zahv.alex.socialnetwork.web.dto.dialogs.DialogMessageRequestDTO
import ru.zahv.alex.socialnetwork.web.dto.dialogs.DialogMessageResponseDTO

@Mapper(componentModel = "spring")
interface DialogMessageMapper {

    @Mapping(target = "fromUserId", source = "from")
    @Mapping(target = "toUserId", source = "to")
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "createDate", expression = "java(java.time.LocalDateTime.now())")
    fun mapToEntity(dto: DialogMessageRequestDTO): DialogMessageEntity

    @Mapping(target = "from", source = "fromUserId")
    @Mapping(target = "to", source = "toUserId")
    @Mapping(target = "createDate", expression = "java(java.time.LocalDateTime.now())")
    fun mapToResponseDTO(entity: DialogMessageEntity): DialogMessageResponseDTO
}