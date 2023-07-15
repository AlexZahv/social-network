package ru.zahv.alex.socialnetwork.web.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

data class UserRegisterResponseDTO(
    @Schema(name = "user_id", example = "e4d2e6b0-cde2-42c5-aac3-0b8316f21e58", required = false)
    @JsonProperty("user_id")
    val userId: String,
)
