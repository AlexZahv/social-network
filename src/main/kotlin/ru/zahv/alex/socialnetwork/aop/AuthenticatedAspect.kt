package ru.zahv.alex.socialnetwork.aop

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import ru.zahv.alex.socialnetwork.business.service.AuthService

@Aspect
@Component
class AuthenticatedAspect(val authService: AuthService) {
    @Pointcut("@annotation(ru.zahv.alex.socialnetwork.aop.Authenticated)")
    fun annotatedMethod() {
    }

    @Before("annotatedMethod()")
    fun checkIsAuthenticated() {
        authService.checkIsAuthenticated()
    }
}
