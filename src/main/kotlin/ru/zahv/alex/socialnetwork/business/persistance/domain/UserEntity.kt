package ru.zahv.alex.socialnetwork.business.persistance.domain

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "users")
data class UserEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        var id: String? = null,
        var firstName: String? = null,
        var secondName: String? = null,

        var sex: String? = null,
        var biography: String? = null,

        @Column(name = "birth_date")
        var birthdate: LocalDate? = null,
        var city: String? = null,
        var age: Int? = null,
        var password: String? = null
)
