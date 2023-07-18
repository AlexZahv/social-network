package ru.zahv.alex.socialnetwork.business.persistance.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "auth_tokens")
data class AuthTokenEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null,
    var issueDate: LocalDateTime? = null,
    var expireDate: LocalDateTime? = null,
    var userId: String? = null,
)
