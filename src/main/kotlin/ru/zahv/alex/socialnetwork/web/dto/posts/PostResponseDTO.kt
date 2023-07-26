package ru.zahv.alex.socialnetwork.web.dto.posts

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

data class PostResponseDTO(

    /**
     * Идентификатор поста
     * @return id
     */
    @Schema(
        name = "id",
        example = "1d535fd6-7521-4cb1-aa6d-031be7123c4d",
        description = "Идентификатор поста",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @JsonProperty("id")
    private val id: String? = null,

    /**
     * Текст поста
     * @return text
     */
    @Schema(
        name = "text",
        example = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lectus mauris ultrices eros in cursus turpis massa.",
        description = "Текст поста",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @JsonProperty("text")
    private val text: String? = null,

    /**
     * Идентификатор пользователя
     * @return authorUserId
     */
    @Schema(
        name = "author_user_id",
        description = "Идентификатор пользователя",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @JsonProperty("author_user_id")
    private val authorUserId: String? = null
)