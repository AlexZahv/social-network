package ru.zahv.alex.socialnetwork.business.persistance.repository

import ru.zahv.alex.socialnetwork.business.persistance.domain.AuthTokenEntity
import java.time.LocalDateTime

interface AuthTokenDao {
    fun findFirstByUserIdAndExpireDateGreaterThan(
            userId: String,
            date: LocalDateTime? = LocalDateTime.now()
    ): AuthTokenEntity?

    fun updateAllByUserIdAndExpireDate(userId: String,
                                       date: LocalDateTime? = LocalDateTime.now())

    fun findFirstByValue(value: String): AuthTokenEntity?

    fun insert(authTokenEntity: AuthTokenEntity): AuthTokenEntity

    fun update(authTokenEntity: AuthTokenEntity): AuthTokenEntity
}