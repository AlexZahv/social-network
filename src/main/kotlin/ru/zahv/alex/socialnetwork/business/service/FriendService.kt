package ru.zahv.alex.socialnetwork.business.service

interface FriendService {
    fun addFriendShip(userId: String, friendId: String)

    fun deleteFriendShip(userId: String, friendId: String)
}