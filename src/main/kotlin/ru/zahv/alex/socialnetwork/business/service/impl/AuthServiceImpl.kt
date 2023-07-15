package ru.zahv.alex.socialnetwork.business.service.impl

import org.springframework.stereotype.Service
import ru.zahv.alex.socialnetwork.business.service.AuthService
import ru.zahv.alex.socialnetwork.business.service.AuthTokenService
import ru.zahv.alex.socialnetwork.business.service.UserService
import ru.zahv.alex.socialnetwork.web.dto.LoginRequestDTO
import ru.zahv.alex.socialnetwork.web.dto.LoginResponseDTO

@Service
class AuthServiceImpl(
    val userService: UserService,
    val tokenService: AuthTokenService,
) : AuthService {
    override fun login(loginRequestDTO: LoginRequestDTO): LoginResponseDTO {
        val userInfo = userService.getUserByIdAndPassword(loginRequestDTO.id, loginRequestDTO.password)
        val tokenEntity = tokenService.createToken(userInfo.id!!)
        return LoginResponseDTO(tokenEntity.value)
    }
}
