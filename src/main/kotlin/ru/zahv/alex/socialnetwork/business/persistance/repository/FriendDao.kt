package ru.zahv.alex.socialnetwork.business.persistance.repository

interface FriendDao {
    fun addFriendShip(userId: String, friendId: String)

    fun deleteFriendShip(userId: String, friendId: String)
}