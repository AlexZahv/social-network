package ru.zahv.alex.socialnetwork.business.persistance.repository

import ru.zahv.alex.socialnetwork.business.persistance.domain.UserEntity

interface UserDao {
    fun findFirstById(id: String): UserEntity?

    fun insert(userEntity: UserEntity):UserEntity

    fun update(userEntity: UserEntity):UserEntity
}