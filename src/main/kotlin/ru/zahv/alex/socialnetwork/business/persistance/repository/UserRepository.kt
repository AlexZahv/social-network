package ru.zahv.alex.socialnetwork.business.persistance.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.zahv.alex.socialnetwork.business.persistance.domain.UserEntity
import java.util.*

@Repository
interface UserRepository : CrudRepository<UserEntity, UUID> {
    fun findFirstById(id: UUID): UserEntity?
}