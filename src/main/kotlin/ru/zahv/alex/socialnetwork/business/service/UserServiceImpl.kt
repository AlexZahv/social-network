package ru.zahv.alex.socialnetwork.business.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.zahv.alex.socialnetwork.business.exceptions.UserNotFountException
import ru.zahv.alex.socialnetwork.business.mapper.UserMapper
import ru.zahv.alex.socialnetwork.business.persistance.repository.UserRepository
import ru.zahv.alex.socialnetwork.web.dto.UserRegisterRequestDTO
import ru.zahv.alex.socialnetwork.web.dto.UserRegisterResponseDTO
import ru.zahv.alex.socialnetwork.web.dto.UserResponseDTO
import java.util.*

@Service
class UserServiceImpl(
        private val userRepository: UserRepository,
        private val userMapper: UserMapper,
        private val passwordEncoder: PasswordEncoder
) : UserService {
    override fun registerUser(userRegisterRequestDTO: UserRegisterRequestDTO): UserRegisterResponseDTO {
        val entity = userMapper.mapToEntityFromDTO(userRegisterRequestDTO)
        entity.password = passwordEncoder.encode(userRegisterRequestDTO.password)

        return userMapper.mapToRegisterResponseDTO(userRepository.save(entity))
    }

    override fun getUserById(userId: String): UserResponseDTO {
        val entity = userRepository.findFirstById(UUID.fromString(userId)) ?: throw UserNotFountException()
        return userMapper.mapToResponseDTO(entity)
    }

    override fun getUserByIdAndPassword(userId: String, password: String): UserResponseDTO {
        val entity = userRepository.findFirstById(UUID.fromString(userId))
        if (entity != null) {
            if (passwordEncoder.matches(password, entity.password)) {
                return userMapper.mapToResponseDTO(entity)
            } else {
                throw UserNotFountException()
            }
        } else {
            throw UserNotFountException()
        }
    }
}