package ru.zahv.alex.socialnetwork.business.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.zahv.alex.socialnetwork.web.dto.LoginRequestDTO
import ru.zahv.alex.socialnetwork.web.dto.LoginResponseDTO
import java.util.*

@Service
class AuthServiceImpl(val userService: UserService,
                      val tokenService: AuthTokenService) : AuthService {

    override fun login(loginRequestDTO: LoginRequestDTO): LoginResponseDTO {
        val userInfo = userService.getUserByIdAndPassword(loginRequestDTO.id, loginRequestDTO.password)
        val tokenEntity = tokenService.createToken(UUID.fromString(userInfo.id))
        return LoginResponseDTO(tokenEntity.value)
    }
}