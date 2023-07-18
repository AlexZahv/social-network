package ru.zahv.alex.socialnetwork.business.service.impl

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.zahv.alex.socialnetwork.business.persistance.domain.AuthTokenEntity
import ru.zahv.alex.socialnetwork.business.persistance.repository.plain.AuthTokenJDBCDao
import ru.zahv.alex.socialnetwork.business.service.AuthTokenService
import java.time.LocalDateTime
import java.util.*

@Service
class AuthTokenServiceImpl(private val repository: AuthTokenJDBCDao) : AuthTokenService {
    companion object {
        const val DEFAULT_TOKEN_LIFETIME = 30L
    }

    @Value("\${auth.token.lifetime}")
    private var tokenLifetime: Long = DEFAULT_TOKEN_LIFETIME

    override fun createToken(userId: String): AuthTokenEntity {
        repository.updateAllByUserIdAndExpireDate(userId, LocalDateTime.now())
        val token = repository.findFirstByUserIdAndExpireDateGreaterThan(userId) ?: AuthTokenEntity()
        token.issueDate = LocalDateTime.now()
        token.expireDate = LocalDateTime.now().plusSeconds(tokenLifetime)
        token.userId = userId
        return repository.insert(token)
    }

    override fun getToken(tokenValue: String): AuthTokenEntity? {
        return repository.findFirstByValue(tokenValue)
    }
}
