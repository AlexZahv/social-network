package ru.zahv.alex.socialnetwork.business.persistance.repository.orm

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.zahv.alex.socialnetwork.business.persistance.domain.UserEntity

@Repository
interface UserRepository : CrudRepository<UserEntity, String> {
    fun findFirstById(id: String): UserEntity?

    fun findAllByFirstNameLikeAndSecondNameLikeOrderByIdDesc(firstName: String, secondName: String): List<UserEntity>?
}
