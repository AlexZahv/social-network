package ru.zahv.alex.socialnetwork.business.persistance.repository.plain

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.springframework.stereotype.Repository
import ru.zahv.alex.socialnetwork.business.persistance.domain.AuthTokenEntity
import ru.zahv.alex.socialnetwork.business.persistance.repository.AuthTokenDao
import ru.zahv.alex.socialnetwork.utils.convertSqlDateToLocalDateTime
import java.sql.ResultSet
import java.sql.SQLException
import java.time.LocalDateTime
import java.util.*

@Repository
@ConditionalOnProperty(name = ["orm.enabled"], havingValue = "false", matchIfMissing = true)
class AuthTokenJDBCDao(private val jdbcTemplate: NamedParameterJdbcTemplate) : AuthTokenDao {
    override fun findFirstByUserIdAndExpireDateGreaterThan(userId: String, date: LocalDateTime?): AuthTokenEntity? {
        val namedParameters: SqlParameterSource = MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("date", date)
        val tokenList = jdbcTemplate.query(
                "SELECT * from auth_tokens where user_id = :userId and expire_date >= :date order by expire_date desc",
                namedParameters,
                TokenRowMapper()
        )
        if (tokenList.isNotEmpty()) {
            return tokenList[0]
        }
        return null
    }

    override fun updateAllByUserIdAndExpireDate(userId: String, date: LocalDateTime?) {
        val namedParameters: SqlParameterSource = MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("date", date)
        jdbcTemplate.update(
                "update auth_tokens set expire_date=:date where expire_date >:date and user_id=:userId",
                namedParameters
        )
    }

    override fun findFirstByValue(value: String): AuthTokenEntity? {
        val namedParameters: SqlParameterSource = MapSqlParameterSource().addValue("value", value)
        val tokenList = jdbcTemplate.query(
                "SELECT * from auth_tokens where value = :value",
                namedParameters,
                TokenRowMapper()
        )
        if (tokenList.isNotEmpty()) {
            return tokenList[0]
        }
        return null
    }

    override fun insert(authTokenEntity: AuthTokenEntity): AuthTokenEntity {
        val namedParameters: SqlParameterSource = BeanPropertySqlParameterSource(authTokenEntity)
        authTokenEntity.id = UUID.randomUUID().toString()
        jdbcTemplate.update(
                "insert into " +
                        "auth_tokens (id, user_id, value, expire_date, issue_date) " +
                        "values (:id, :userId, :value, :expireDate, :issueDate)",
                namedParameters
        )
        return authTokenEntity
    }

    override fun update(authTokenEntity: AuthTokenEntity): AuthTokenEntity {
        val namedParameters: SqlParameterSource = BeanPropertySqlParameterSource(authTokenEntity)
        authTokenEntity.id = UUID.randomUUID().toString()
        jdbcTemplate.update(
                "update auth_tokens set" +
                        "user_id=:userId, " +
                        "value=:value, " +
                        "expire_date=:expireDate, " +
                        "issue_date=:issueDate" +
                        "where id:=id",
                namedParameters
        )
        return authTokenEntity
    }
}

class TokenRowMapper : RowMapper<AuthTokenEntity> {
    @Throws(SQLException::class)
    override fun mapRow(rs: ResultSet, rowNum: Int): AuthTokenEntity {
        val tokenEntity = AuthTokenEntity()
        tokenEntity.id = rs.getString("ID")
        tokenEntity.value = rs.getString("VALUE")
        tokenEntity.userId = rs.getString("USER_ID")
        tokenEntity.expireDate = convertSqlDateToLocalDateTime(rs.getTimestamp("EXPIRE_DATE"))
        tokenEntity.issueDate = convertSqlDateToLocalDateTime(rs.getTimestamp("ISSUE_DATE"))
        return tokenEntity
    }

}