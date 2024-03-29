package ru.zahv.alex.socialnetwork.web.dto.login

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

data class LoginResponseDTO(
    @Schema(name = "token", example = "e4d2e6b0-cde2-42c5-aac3-0b8316f21e58", required = false)
    @JsonProperty("token")
    val token: String? = null,
)
