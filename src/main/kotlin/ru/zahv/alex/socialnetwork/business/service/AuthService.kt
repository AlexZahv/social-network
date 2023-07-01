package ru.zahv.alex.socialnetwork.business.service

import ru.zahv.alex.socialnetwork.web.dto.LoginRequestDTO
import ru.zahv.alex.socialnetwork.web.dto.LoginResponseDTO

interface AuthService {
    fun login(loginRequestDTO: LoginRequestDTO): LoginResponseDTO
}