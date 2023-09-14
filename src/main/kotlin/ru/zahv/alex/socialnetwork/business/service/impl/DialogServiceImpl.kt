package ru.zahv.alex.socialnetwork.business.service.impl

import org.springframework.stereotype.Service
import ru.zahv.alex.socialnetwork.business.exceptions.DialogMessageException
import ru.zahv.alex.socialnetwork.business.service.DialogService
import ru.zahv.alex.socialnetwork.business.service.external.DialogsFeignClient
import ru.zahv.alex.socialnetwork.utils.SecurityContextHolder.Companion.getCurrentUser
import ru.zahv.alex.socialnetwork.web.dto.dialogs.DialogMessageRequestDTO
import ru.zahv.alex.socialnetwork.web.dto.dialogs.DialogMessageResponseDTO

@Service
class DialogServiceImpl(
   val dialogsFeignClient: DialogsFeignClient
) : DialogService {
    override fun addMessage(dto: DialogMessageRequestDTO) {
        if (dto.from != getCurrentUser()) {
            throw DialogMessageException("В качестве отправителя сообщения может быть указан только текущий пользователь")
        }
        dialogsFeignClient.sendMessage(dto)
    }

    override fun getAllMessageList(userId: String): List<DialogMessageResponseDTO> {
        return dialogsFeignClient.getDialog(userId, getCurrentUser())
    }
}
