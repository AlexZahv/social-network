package ru.zahv.alex.socialnetwork.business.persistance.repository.orm

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.zahv.alex.socialnetwork.business.persistance.domain.UserEntity
import ru.zahv.alex.socialnetwork.business.persistance.repository.UserDao

@Repository
@ConditionalOnProperty(name = ["orm.enabled"], havingValue = "true", matchIfMissing = true)
class UserJPADao(private val userRepository: UserRepository) : UserDao {
    override fun findFirstById(id: String): UserEntity? {
        return userRepository.findFirstById(id)
    }

    override fun findAllByFirstNameAndLastName(firstName: String, lastName: String): List<UserEntity>? {
        return userRepository.findAllByFirstNameLikeAndSecondNameLikeOrderByIdDesc(firstName, lastName)
    }

    @Transactional
    override fun insert(userEntity: UserEntity): UserEntity {
        return userRepository.save(userEntity)
    }

    @Transactional
    override fun update(userEntity: UserEntity): UserEntity {
        return userRepository.save(userEntity)
    }
}
