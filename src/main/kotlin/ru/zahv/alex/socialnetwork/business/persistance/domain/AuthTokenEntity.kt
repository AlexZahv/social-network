package ru.zahv.alex.socialnetwork.business.persistance.domain

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "auth_tokens")
data class AuthTokenEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,
    var value: String? = null,
    var issueDate: LocalDateTime? = null,
    var expireDate: LocalDateTime? = null,
    var userId: String? = null
)