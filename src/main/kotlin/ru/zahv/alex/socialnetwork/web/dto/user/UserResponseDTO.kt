package ru.zahv.alex.socialnetwork.web.dto.user

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

data class UserResponseDTO(
    @Schema(name = "id", description = "Идентификатор пользователя", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("id")
    var id: String? = null,

    @Schema(name = "first_name", example = "Имя", description = "Имя", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("first_name")
    var firstName: String? = null,

    @Schema(name = "second_name", example = "Фамилия", description = "Фамилия", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("second_name")
    var secondName: String? = null,

    @Schema(name = "age", example = "18", description = "Возраст", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("age")
    var age: Int? = null,

    @Schema(name = "sex", example = "MALE", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("sex")
    var sex: String? = null,

    @Schema(name = "birthdate", example = "Wed Feb 01 03:00:00 MSK 2017", description = "Дата рождения", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("birthdate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    var birthdate: LocalDate? = null,

    @Schema(name = "biography", example = "Хобби, интересы и т.п.", description = "Интересы", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("biography")
    var biography: String? = null,

    @Schema(name = "city", example = "Москва", description = "Город", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("city")
    var city: String? = null,

    @Schema(name = "isCelebrity", example = "true", description = "Пользователь является знаменитостью", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("isCelebrity")
    var isCelebrity: Boolean = false
)
