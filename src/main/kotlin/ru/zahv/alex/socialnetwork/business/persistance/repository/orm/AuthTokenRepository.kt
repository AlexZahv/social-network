package ru.zahv.alex.socialnetwork.business.persistance.repository.orm

import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.zahv.alex.socialnetwork.business.persistance.domain.AuthTokenEntity
import java.time.LocalDateTime
import java.util.*

@Repository
interface AuthTokenRepository : CrudRepository<AuthTokenEntity, UUID> {
    fun findFirstByUserIdAndExpireDateGreaterThan(
        userId: String,
        date: LocalDateTime? = LocalDateTime.now(),
    ): AuthTokenEntity?

    @Transactional
    @Modifying
    @Query("update AuthTokenEntity a set a.expireDate = ?2 where a.userId = ?1 and a.expireDate>?2")
    fun updateAllByUserIdAndExpireDate(
        userId: String,
        date: LocalDateTime? = LocalDateTime.now(),
    )

    fun findFirstById(value: String): AuthTokenEntity?
}
