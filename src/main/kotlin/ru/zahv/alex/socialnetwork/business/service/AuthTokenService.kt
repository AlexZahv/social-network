package ru.zahv.alex.socialnetwork.business.service

import ru.zahv.alex.socialnetwork.business.persistance.domain.AuthTokenEntity

/**
 * @author azakhvalinskiy
 * Service, which contains methods for work with auth token domain model
 */
interface AuthTokenService {
    fun createToken(userId: String): AuthTokenEntity
    fun getToken(tokenValue: String): AuthTokenEntity?
}
