package ru.zahv.alex.socialnetwork.business.service

import ru.zahv.alex.socialnetwork.web.dto.UserRegisterRequestDTO
import ru.zahv.alex.socialnetwork.web.dto.UserRegisterResponseDTO
import ru.zahv.alex.socialnetwork.web.dto.UserResponseDTO

/**
 * @author azakhvalinskiy
 * Service, which contains logic for working with User domain
 */
interface UserService {
    /**
     * Register new User
     * @param {@link UserRegisterRequestDTO}
     * @return {@link UserRegisterResponseDTO}
     */
    fun registerUser(userRegisterRequestDTO: UserRegisterRequestDTO): UserRegisterResponseDTO

    /**
     * Get user by id
     * @param userId {@link String}
     * @return {@link UserResponseDTO}
     */
    fun getUserById(userId: String): UserResponseDTO

    /**
     * Get user by id and password
     * @param userId {@link String}
     * @param password {@link String}
     * @return {@link UserResponseDTO}
     */
    fun getUserByIdAndPassword(userId: String, password: String): UserResponseDTO

    /**
     * Search user by firstName and secondName
     * @param firstName {@link String}
     * @param lastName {@link String}
     * @return {@link List<UserResponseDTO>}
     */
    fun searchUsersByFirstNameAndLastName(firstName: String, lastName: String): List<UserResponseDTO>?
}
