package ru.zahv.alex.socialnetwork.business.persistance.repository.orm

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Repository
import ru.zahv.alex.socialnetwork.business.persistance.domain.AuthTokenEntity
import ru.zahv.alex.socialnetwork.business.persistance.repository.AuthTokenDao
import java.time.LocalDateTime

@Repository
@ConditionalOnProperty(name = ["orm.enabled"], havingValue = "true", matchIfMissing = true)
class AuthTokenJPADao(private val authTokenRepository: AuthTokenRepository) : AuthTokenDao {
    override fun findFirstByUserIdAndExpireDateGreaterThan(userId: String, date: LocalDateTime?): AuthTokenEntity? {
        return authTokenRepository.findFirstByUserIdAndExpireDateGreaterThan(userId, date)
    }

    override fun updateAllByUserIdAndExpireDate(userId: String, date: LocalDateTime?) {
        authTokenRepository.updateAllByUserIdAndExpireDate(userId, date)
    }

    override fun findFirstByValue(value: String): AuthTokenEntity? {
        return authTokenRepository.findFirstById(value)
    }

    override fun insert(authTokenEntity: AuthTokenEntity): AuthTokenEntity {
        return authTokenRepository.save(authTokenEntity)
    }

    override fun update(authTokenEntity: AuthTokenEntity): AuthTokenEntity {
        return authTokenRepository.save(authTokenEntity)
    }
}
