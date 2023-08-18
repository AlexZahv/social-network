package ru.zahv.alex.socialnetwork.business.service

import ru.zahv.alex.socialnetwork.web.dto.LoginRequestDTO
import ru.zahv.alex.socialnetwork.web.dto.LoginResponseDTO

/**
 * @author azakhvalinskiy
 * Service, which presents methods for user authentication
 */
interface AuthService {
    fun login(loginRequestDTO: LoginRequestDTO): LoginResponseDTO
    fun checkIsAuthenticated()
    fun checkIsAuthenticated(authHeader: String?)
}
