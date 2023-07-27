package ru.zahv.alex.socialnetwork.business.service

/**
 * @author azakhvalinskiy
 * Service contains logic for control friendship relations between users
 */
interface FriendService {
    /**
     * Add new friendship relation between users
     * @param userId - user id
     * @param friendId - friend id
     */
    fun addFriendShip(userId: String, friendId: String)

    /**
     * Delete friendship relation between users
     * @param userId - user id
     * @param friendId - friend id
     */
    fun deleteFriendShip(userId: String, friendId: String)
}