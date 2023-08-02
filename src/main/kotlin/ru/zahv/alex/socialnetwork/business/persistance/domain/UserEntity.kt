package ru.zahv.alex.socialnetwork.business.persistance.domain

import java.time.LocalDate

data class UserEntity(
    var id: String? = null,
    var firstName: String? = null,
    var secondName: String? = null,

    var sex: String? = null,
    var biography: String? = null,

    var birthdate: LocalDate? = null,
    var city: String? = null,
    var age: Int? = null,
    var password: String? = null,
)
