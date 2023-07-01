package ru.zahv.alex.socialnetwork.business.persistance.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.zahv.alex.socialnetwork.business.persistance.domain.AuthTokenEntity
import java.time.LocalDateTime
import java.util.*

@Repository
interface AuthTokenRepository : CrudRepository<AuthTokenEntity, UUID> {
    fun findFirstByUserIdAndExpireDateGreaterThanEqual(
            userId: UUID,
            date: LocalDateTime? = LocalDateTime.now()
    ): AuthTokenEntity?
}