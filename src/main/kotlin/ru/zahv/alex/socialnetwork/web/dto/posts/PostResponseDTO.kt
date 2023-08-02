package ru.zahv.alex.socialnetwork.web.dto.posts

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

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
    var id: String? = null,

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
    var text: String? = null,

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
    var authorUserId: String? = null,

    /**
     * Дата создания поста
     * @return createDate
     */
    @Schema(
        name = "create_date",
        description = "Дата создания поста",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    var createDate: LocalDateTime
) : java.io.Serializable