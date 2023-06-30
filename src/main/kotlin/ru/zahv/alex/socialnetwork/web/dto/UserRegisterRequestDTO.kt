package ru.zahv.alex.socialnetwork.web.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import lombok.Getter
import lombok.Setter
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

@Getter
@Setter
class UserRegisterRequestDTO {
    @Schema(name = "first_name", example = "Имя", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("first_name")
    private val firstName: String? = null

    @Schema(name = "second_name", example = "Фамилия", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("second_name")
    private val secondName: String? = null

    @Schema(name = "age", example = "18", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("age")
    private val age: Int? = null

    @Schema(name = "birthdate", example = "Wed Feb 01 03:00:00 MSK 2017", description = "Дата рождения", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("birthdate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private val birthdate: LocalDate? = null

    @Schema(name = "biography", example = "Хобби, интересы и т.п.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("biography")
    private val biography: String? = null

    @Schema(name = "city", example = "Москва", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("city")
    private val city: String? = null

    @Schema(name = "password", example = "Секретная строка", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("password")
    private val password: String? = null
}