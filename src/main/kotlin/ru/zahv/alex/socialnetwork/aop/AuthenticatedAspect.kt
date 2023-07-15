package ru.zahv.alex.socialnetwork.aop

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import ru.zahv.alex.socialnetwork.business.exceptions.CustomAuthorizationException
import ru.zahv.alex.socialnetwork.business.service.AuthTokenService
import java.time.LocalDateTime
import java.util.regex.Pattern

@Aspect
@Component
class AuthenticatedAspect(val authTokenService: AuthTokenService) {
    private val bearerPattern = Pattern.compile("^[Bb]earer (.*?$)")

    @Pointcut("@annotation(ru.zahv.alex.socialnetwork.aop.Authenticated)")
    fun annotatedMethod() {
    }

    @Before("annotatedMethod()")
    fun checkIsAuthenticated() {
        val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
        val authHeader: String? = request.getHeader("Authorization")
        if (authHeader != null && bearerPattern.matcher(authHeader).matches()) {
            val matcher = bearerPattern.matcher(authHeader)
            if (matcher.find()) {
                val token = matcher.group(1)
                if (!checkAuthTokenIsValid(token)) {
                    throw CustomAuthorizationException("Ошибка авторизации. Токен доступа невалиден.")
                }
            }
        } else {
            throw CustomAuthorizationException("Ошибка авторизации. Токен доступа не получен.")
        }
    }

    private fun checkAuthTokenIsValid(authToken: String): Boolean {
        val storedToken = authTokenService.getToken(authToken)
        return storedToken != null && LocalDateTime.now().isBefore(storedToken.expireDate)
    }
}
