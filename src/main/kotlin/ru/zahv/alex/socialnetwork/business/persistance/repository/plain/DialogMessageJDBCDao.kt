package ru.zahv.alex.socialnetwork.business.persistance.repository.plain

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.springframework.stereotype.Repository
import ru.zahv.alex.socialnetwork.business.persistance.domain.DialogMessageEntity
import ru.zahv.alex.socialnetwork.business.persistance.repository.DialogMessageDao
import ru.zahv.alex.socialnetwork.utils.getDialogId
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*

@Repository
@ConditionalOnProperty(name = ["orm.enabled"], havingValue = "false", matchIfMissing = true)
class DialogMessageJDBCDao(
    @Qualifier("masterTemplate") private val masterTemplate: NamedParameterJdbcTemplate,
    @Qualifier("slaveTemplate") private val slaveTemplate: NamedParameterJdbcTemplate
) : DialogMessageDao {
    override fun addMessage(entity: DialogMessageEntity): DialogMessageEntity {
        val namedParameters: SqlParameterSource = BeanPropertySqlParameterSource(entity)
        masterTemplate.update(
            "insert into " +
                    "dialog_message (id, dialog_id, message_text, from_user_id, to_user_id, create_date) " +
                    "values (:id, :dialogId, :text, :fromUserId, :toUserId, :createDate) ",
            namedParameters,
        )

        return entity
    }

    override fun getAllMessageList(userId: String, currentUserId: String): List<DialogMessageEntity>? {
        val namedParameters: SqlParameterSource = MapSqlParameterSource()
            .addValue("dialogId", getDialogId(userId, currentUserId))

        return slaveTemplate.query(
            "SELECT * from dialog_message " +
                    "where dialog_id=:dialogId " +
                    " order by create_date desc",
            namedParameters,
            DialogMessageRowMapper(),
        )
    }
}

class DialogMessageRowMapper : RowMapper<DialogMessageEntity> {
    @Throws(SQLException::class)
    override fun mapRow(rs: ResultSet, rowNum: Int): DialogMessageEntity {
        val messageEntity = DialogMessageEntity()
        messageEntity.id = rs.getString("ID")
        messageEntity.fromUserId = rs.getString("FROM_USER_ID")
        messageEntity.toUserId = rs.getString("TO_USER_ID")
        messageEntity.createDate = rs.getTimestamp("CREATE_DATE").toLocalDateTime()
        messageEntity.text = rs.getString("MESSAGE_TEXT")
        return messageEntity
    }
}
