package ru.zahv.alex.socialnetwork.business.persistance.repository.plain

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.springframework.stereotype.Repository
import ru.zahv.alex.socialnetwork.business.persistance.domain.FriendshipEntity
import ru.zahv.alex.socialnetwork.business.persistance.repository.FriendDao

@Repository
@ConditionalOnProperty(name = ["orm.enabled"], havingValue = "false", matchIfMissing = true)
class FriendJDBCDao(
    @Qualifier("masterTemplate") private val masterTemplate: NamedParameterJdbcTemplate
) : FriendDao {

    override fun addFriendShip(userId: String, friendId: String) {
        val entity = FriendshipEntity(userId, friendId);
        val namedParameters: SqlParameterSource = BeanPropertySqlParameterSource(entity)
        masterTemplate.update(
            "insert into " +
                    "friendship (user_id, friend_id) " +
                    "values (:userId, :friendId) " +
                    "ON CONFLICT DO NOTHING",
            namedParameters,
        )
    }

    override fun deleteFriendShip(userId: String, friendId: String) {
        val entity = FriendshipEntity(userId, friendId);
        val namedParameters: SqlParameterSource = BeanPropertySqlParameterSource(entity)
        masterTemplate.update(
            "delete from friendship " +
                    "where user_id=:userId and friend_id=:friendId",
            namedParameters,
        )
    }
}