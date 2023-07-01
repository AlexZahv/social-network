package ru.zahv.alex.socialnetwork.business.persistance.domain

import jakarta.persistence.*
import ru.zahv.alex.socialnetwork.business.enums.SexEnum
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "users")
data class UserEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        var id: UUID? = null,
        var firstName: String? = null,
        var secondName: String? = null,

        @Enumerated(EnumType.STRING)
        var sex: SexEnum? = null,
        var biography: String? = null,

        @Column(name = "birth_date")
        var birthdate: LocalDate? = null,
        var city: String? = null,
        var age: Int? = null,
        var password: String? = null
)
