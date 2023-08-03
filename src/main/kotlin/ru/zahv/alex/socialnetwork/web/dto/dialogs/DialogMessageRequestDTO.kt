package ru.zahv.alex.socialnetwork.web.dto.dialogs

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull

data class DialogMessageRequestDTO(

    /**
     * Идентификатор пользователя
     * @return from
     */
    @NotNull
    @Schema(name = "from", description = "Идентификатор пользователя", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("from")
    var from: String? = null,

    /**
     * Идентификатор пользователя
     * @return to
     */
    @NotNull
    @Schema(name = "to", description = "Идентификатор пользователя", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("to")
   var to: String? = null,

    /**
     * Текст сообщения
     * @return text
     */
    @NotNull
    @Schema(
        name = "text",
        example = "Привет, как дела?",
        description = "Текст сообщения",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @JsonProperty("text")
    var text: String? = null
)