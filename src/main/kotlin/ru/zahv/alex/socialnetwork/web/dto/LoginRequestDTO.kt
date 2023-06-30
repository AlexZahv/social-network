package ru.zahv.alex.socialnetwork.web.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import lombok.Getter
import lombok.Setter

@Getter
@Setter
class LoginRequestDTO {
    @Schema(name = "id", description = "Идентификатор пользователя", required = false)
    @JsonProperty("id")
    private val id: String? = null

    @Schema(name = "password", example = "Секретная строка", required = false)
    @JsonProperty("password")
    private val password: String? = null
}