package ru.zahv.alex.socialnetwork.business.service.impl

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.zahv.alex.socialnetwork.business.exceptions.InvalidPasswordException
import ru.zahv.alex.socialnetwork.business.exceptions.UserNotFoundException
import ru.zahv.alex.socialnetwork.business.mapper.UserMapper
import ru.zahv.alex.socialnetwork.business.persistance.repository.UserRepository
import ru.zahv.alex.socialnetwork.business.service.UserService
import ru.zahv.alex.socialnetwork.web.dto.UserRegisterRequestDTO
import ru.zahv.alex.socialnetwork.web.dto.UserRegisterResponseDTO
import ru.zahv.alex.socialnetwork.web.dto.UserResponseDTO

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
        val entity = userRepository.findFirstById(userId)
                ?: throw UserNotFoundException("Анкета не найдена")
        return userMapper.mapToResponseDTO(entity)
    }

    override fun getUserByIdAndPassword(userId: String, password: String): UserResponseDTO {
        val entity = userRepository.findFirstById(userId)
        if (entity != null) {
            if (passwordEncoder.matches(password, entity.password)) {
                return userMapper.mapToResponseDTO(entity)
            } else {
                throw InvalidPasswordException()
            }
        } else {
            throw UserNotFoundException()
        }
    }
}