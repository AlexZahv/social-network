package ru.zahv.alex.socialnetwork.business.service.impl

import org.springframework.stereotype.Service
import ru.zahv.alex.socialnetwork.business.persistance.repository.FriendDao
import ru.zahv.alex.socialnetwork.business.service.FriendService

@Service
class FriendServiceImpl(
    val friendDao: FriendDao
) : FriendService {

    override fun addFriendShip(userId: String, friendId: String) {
        friendDao.addFriendShip(userId, friendId)
    }

    override fun deleteFriendShip(userId: String, friendId: String) {
        friendDao.deleteFriendShip(userId, friendId)
    }
}