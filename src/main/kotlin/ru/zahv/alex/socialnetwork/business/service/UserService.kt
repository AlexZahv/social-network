package ru.zahv.alex.socialnetwork.business.service

import ru.zahv.alex.socialnetwork.web.dto.UserRegisterRequestDTO
import ru.zahv.alex.socialnetwork.web.dto.UserRegisterResponseDTO
import ru.zahv.alex.socialnetwork.web.dto.UserResponseDTO

/**
 * @author azakhvalinskiy
 * Service, which contains logic for working with User domain
 */
interface UserService {
    fun registerUser(userRegisterRequestDTO: UserRegisterRequestDTO): UserRegisterResponseDTO
    fun getUserById(userId: String): UserResponseDTO
    fun getUserByIdAndPassword(userId: String, password: String): UserResponseDTO
}