package ru.zahv.alex.socialnetwork.web.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import lombok.Getter
import lombok.Setter

data class LoginRequestDTO (
    @Schema(name = "id", description = "Идентификатор пользователя", required = false)
    @JsonProperty("id")
    val id: String,

    @Schema(name = "password", example = "Секретная строка", required = false)
    @JsonProperty("password")
    val password: String
)