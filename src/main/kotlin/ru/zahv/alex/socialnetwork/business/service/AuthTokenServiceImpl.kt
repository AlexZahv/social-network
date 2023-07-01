package ru.zahv.alex.socialnetwork.business.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.zahv.alex.socialnetwork.business.persistance.domain.AuthTokenEntity
import ru.zahv.alex.socialnetwork.business.persistance.domain.UserEntity
import ru.zahv.alex.socialnetwork.business.persistance.repository.AuthTokenRepository
import java.time.LocalDateTime
import java.util.*

@Service
class AuthTokenServiceImpl(private val repository: AuthTokenRepository) : AuthTokenService {
    companion object {
        const val DEFAULT_TOKEN_LIFETIME = 30L
    }

    @Value("\${auth.token.lifetime}")
    private var tokenLifetime: Long = DEFAULT_TOKEN_LIFETIME

    override fun createToken(userId: UUID): AuthTokenEntity {
        val token = repository.findFirstByUserIdAndExpireDateGreaterThanEqual(userId) ?: AuthTokenEntity()
        token.issueDate = LocalDateTime.now()
        token.expireDate = LocalDateTime.now().plusSeconds(tokenLifetime)
        token.userId = userId
        token.value = UUID.randomUUID().toString()
        return repository.save(token)
    }
}