package ru.zahv.alex.socialnetwork.business.service.impl

import org.springframework.stereotype.Service
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import ru.zahv.alex.socialnetwork.business.exceptions.CustomAuthorizationException
import ru.zahv.alex.socialnetwork.business.service.AuthService
import ru.zahv.alex.socialnetwork.business.service.AuthTokenService
import ru.zahv.alex.socialnetwork.business.service.UserService
import ru.zahv.alex.socialnetwork.utils.SecurityContextHolder
import ru.zahv.alex.socialnetwork.web.dto.login.LoginRequestDTO
import ru.zahv.alex.socialnetwork.web.dto.login.LoginResponseDTO
import java.time.LocalDateTime
import java.util.regex.Pattern

@Service
class AuthServiceImpl(
    val userService: UserService,
    val tokenService: AuthTokenService,
) : AuthService {
    private val bearerPattern = Pattern.compile("^[Bb]earer (.*?$)")

    override fun login(loginRequestDTO: LoginRequestDTO): LoginResponseDTO {
        val userInfo = userService.getUserByIdAndPassword(loginRequestDTO.id, loginRequestDTO.password)
        val tokenEntity = tokenService.createToken(userInfo.id!!)
        return LoginResponseDTO(tokenEntity.id)
    }

    override fun checkIsAuthenticated() {
        val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
        val authHeader: String? = request.getHeader("Authorization")
        checkIsAuthenticated(authHeader)
    }

    override fun checkIsAuthenticated(authHeader: String?) {
        if (authHeader != null && bearerPattern.matcher(authHeader).matches()) {
            val matcher = bearerPattern.matcher(authHeader)
            if (matcher.find()) {
                val token = matcher.group(1)
                checkAuthTokenIsValid(token)
            }
        } else {
            throw CustomAuthorizationException("Ошибка авторизации. Токен доступа не получен.")
        }
    }

    private fun checkAuthTokenIsValid(authToken: String) {
        val storedToken = tokenService.getToken(authToken)
        if (storedToken == null || !LocalDateTime.now().isBefore(storedToken.expireDate)) {
            throw CustomAuthorizationException("Ошибка авторизации. Токен доступа невалиден.")
        } else {
            SecurityContextHolder.storeCurrentUser(storedToken.userId!!)
        }
    }
}
