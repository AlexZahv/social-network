package ru.zahv.alex.socialnetwork.business.service.external

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import ru.zahv.alex.socialnetwork.web.dto.dialogs.DialogMessageRequestDTO
import ru.zahv.alex.socialnetwork.web.dto.dialogs.DialogMessageResponseDTO

@FeignClient(name = "dialogs-client", url = "\${services.dialogs}/api/dialog")
interface DialogsFeignClient {

    @GetMapping
    fun getDialog(
            @RequestParam("userId") userId: String,
            @RequestParam currentUserId: String
    ): List<DialogMessageResponseDTO>

    @PostMapping(value = ["/send"])
    fun sendMessage(@RequestBody(required = false) dto: DialogMessageRequestDTO)
}
