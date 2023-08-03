package ru.zahv.alex.socialnetwork.business.service.impl

import org.springframework.stereotype.Service
import ru.zahv.alex.socialnetwork.business.mapper.DialogMessageMapper
import ru.zahv.alex.socialnetwork.business.persistance.repository.DialogMessageDao
import ru.zahv.alex.socialnetwork.business.service.DialogService
import ru.zahv.alex.socialnetwork.utils.SecurityContextHolder
import ru.zahv.alex.socialnetwork.web.dto.dialogs.DialogMessageRequestDTO
import ru.zahv.alex.socialnetwork.web.dto.dialogs.DialogMessageResponseDTO

@Service
class DialogServiceImpl(
    private val dialogMessageDao: DialogMessageDao,
    private val dialogMessageMapper: DialogMessageMapper
) : DialogService {
    override fun addMessage(dto: DialogMessageRequestDTO): DialogMessageResponseDTO {
        return dialogMessageMapper.mapToResponseDTO(dialogMessageDao.addMessage(dialogMessageMapper.mapToEntity(dto)))
    }

    override fun getAllMessageList(userId: String): List<DialogMessageResponseDTO> {
        return dialogMessageDao.getAllMessageList(userId, SecurityContextHolder.getCurrentUser())
            ?.map { dialogMessageEntity ->
                dialogMessageMapper.mapToResponseDTO(dialogMessageEntity)
            }?.toList() ?: listOf()
    }
}