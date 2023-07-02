package ru.zahv.alex.socialnetwork.business.persistance.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.zahv.alex.socialnetwork.business.persistance.domain.UserEntity

@Repository
interface UserRepository : CrudRepository<UserEntity, String> {
    fun findFirstById(id: String): UserEntity?
}