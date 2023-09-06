package ru.zahv.alex.socialnetwork.web.dto.user

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.format.annotation.DateTimeFormat
import ru.zahv.alex.socialnetwork.business.enums.SexEnum
import java.time.LocalDate

data class UserRegisterRequestDTO(

    @Schema(name = "first_name", example = "Имя", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("first_name")
    val firstName: String,

    @Schema(name = "second_name", example = "Фамилия", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("second_name")
    val secondName: String,

    @Schema(name = "sex", example = "MALE", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("sex")
    val sex: SexEnum,

    @Schema(name = "age", example = "18", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("age")
    val age: Int,

    @Schema(name = "birthdate", example = "Wed Feb 01 03:00:00 MSK 2017", description = "Дата рождения", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("birthdate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    val birthdate: LocalDate,

    @Schema(name = "biography", example = "Хобби, интересы и т.п.", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("biography")
    val biography: String,

    @Schema(name = "city", example = "Москва", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("city")
    val city: String,

    @Schema(name = "password", example = "Секретная строка", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("password")
    val password: String,
)
