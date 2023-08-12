package ru.zahv.alex.socialnetwork.business.service.impl

import org.springframework.stereotype.Service
import ru.zahv.alex.socialnetwork.business.exceptions.DialogMessageException
import ru.zahv.alex.socialnetwork.business.mapper.DialogMessageMapper
import ru.zahv.alex.socialnetwork.business.persistance.repository.DialogMessageDao
import ru.zahv.alex.socialnetwork.business.service.DialogService
import ru.zahv.alex.socialnetwork.utils.SecurityContextHolder.Companion.getCurrentUser
import ru.zahv.alex.socialnetwork.web.dto.dialogs.DialogMessageRequestDTO
import ru.zahv.alex.socialnetwork.web.dto.dialogs.DialogMessageResponseDTO

@Service
class DialogServiceImpl(
    private val dialogMessageDao: DialogMessageDao,
    private val dialogMessageMapper: DialogMessageMapper
) : DialogService {
    override fun addMessage(dto: DialogMessageRequestDTO): DialogMessageResponseDTO {
        if (dto.from != getCurrentUser()) {
            throw DialogMessageException("В качестве отправителя сообщения может быть указан только текущий пользователь")
        }
        return dialogMessageMapper.mapToResponseDTO(dialogMessageDao.addMessage(dialogMessageMapper.mapToEntity(dto)))
    }

    override fun getAllMessageList(userId: String): List<DialogMessageResponseDTO> {
        return dialogMessageDao.getAllMessageList(userId, getCurrentUser())
            ?.map { dialogMessageEntity ->
                dialogMessageMapper.mapToResponseDTO(dialogMessageEntity)
            }?.toList() ?: listOf()
    }
}