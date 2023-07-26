package ru.zahv.alex.socialnetwork.web.dto.posts

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull

data class PostCreateRequestDTO(
    @NotNull
    @Schema(
        name = "text",
        example = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lectus mauris ultrices eros in cursus turpis massa.",
        description = "Текст поста",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @JsonProperty("text")
    val text: String? = null
)