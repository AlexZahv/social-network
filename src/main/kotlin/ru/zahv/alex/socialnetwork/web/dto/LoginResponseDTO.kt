package ru.zahv.alex.socialnetwork.web.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import lombok.Getter
import lombok.Setter

@Getter
@Setter
class LoginResponseDTO {
    @Schema(name = "token", example = "e4d2e6b0-cde2-42c5-aac3-0b8316f21e58", required = false)
    @JsonProperty("token")
    private val token: String? = null

}