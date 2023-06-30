package ru.zahv.alex.socialnetwork.web.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import lombok.Getter
import lombok.Setter
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

@Getter
@Setter
class UserResponseDTO {
    @Schema(name = "id", description = "Идентификатор пользователя", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("id")
    private val id: String? = null

    @Schema(name = "first_name", example = "Имя", description = "Имя", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("first_name")
    private val firstName: String? = null

    @Schema(name = "second_name", example = "Фамилия", description = "Фамилия", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("second_name")
    private val secondName: String? = null

    @Schema(name = "age", example = "18", description = "Возраст", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("age")
    private val age: Int? = null

    @Schema(name = "birthdate", example = "Wed Feb 01 03:00:00 MSK 2017", description = "Дата рождения", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("birthdate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private val birthdate: LocalDate? = null

    @Schema(name = "biography", example = "Хобби, интересы и т.п.", description = "Интересы", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("biography")
    private val biography: String? = null

    @Schema(name = "city", example = "Москва", description = "Город", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("city")
    private val city: String? = null
}