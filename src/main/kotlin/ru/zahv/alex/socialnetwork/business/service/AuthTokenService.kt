package ru.zahv.alex.socialnetwork.business.service

import ru.zahv.alex.socialnetwork.business.persistance.domain.AuthTokenEntity
import java.util.*

interface AuthTokenService {
    fun createToken(userId: UUID): AuthTokenEntity
}