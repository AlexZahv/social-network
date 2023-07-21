package ru.zahv.alex.socialnetwork.business.persistance.repository.plain

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.zahv.alex.socialnetwork.business.persistance.domain.UserEntity
import ru.zahv.alex.socialnetwork.business.persistance.repository.UserDao
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*

@Repository
@ConditionalOnProperty(name = ["orm.enabled"], havingValue = "false", matchIfMissing = true)
class UserJDBCDao(
    @Qualifier("masterTemplate") private val masterTemplate: NamedParameterJdbcTemplate,
    @Qualifier("slaveTemplate") private val slaveTemplate: NamedParameterJdbcTemplate,
) : UserDao {
    override fun findFirstById(id: String): UserEntity? {
        val namedParameters: SqlParameterSource = MapSqlParameterSource().addValue("id", id)
        val userList = masterTemplate.query(
            "SELECT * from users u where id = :id",
            namedParameters,
            UserRowMapper(),
        )
        if (userList.isNotEmpty()) {
            return userList[0]
        }

        return null
    }

    @Transactional
    override fun insert(userEntity: UserEntity): UserEntity {
        val namedParameters: SqlParameterSource = BeanPropertySqlParameterSource(userEntity)
        userEntity.id = UUID.randomUUID().toString()
        masterTemplate.update(
            "insert into " +
                "users (id, first_name, second_name, birth_date, city, biography, sex, age, password) " +
                "values (:id, :firstName, :secondName, :birthdate, :city, :biography, :sex,:age, :password)",
            namedParameters,
        )
        return userEntity
    }

    override fun update(userEntity: UserEntity): UserEntity {
        val namedParameters: SqlParameterSource = BeanPropertySqlParameterSource(userEntity)
        userEntity.id = UUID.randomUUID().toString()
        masterTemplate.update(
            "update users " +
                "set id=:id, " +
                "first_name=:firstName, " +
                "second_name=:secondName, " +
                "birth_date=:birthdate," +
                "city=:city," +
                "biography=:biography," +
                "age=:age," +
                "password:password",
            namedParameters,
        )
        return userEntity
    }

    override fun findAllByFirstNameAndLastName(firstName: String, lastName: String): List<UserEntity>? {
        val namedParameters: SqlParameterSource = MapSqlParameterSource()
            .addValue("firstName", getLikePattern(firstName))
            .addValue("secondName", getLikePattern(lastName))

        return masterTemplate.query(
            "SELECT * from users u where second_name like :secondName and first_name like :firstName " +
                " order by id desc",
            namedParameters,
            UserRowMapper(),
        )
    }

    private fun getLikePattern(param: String): String {
        return """$param%"""
    }
}

class UserRowMapper : RowMapper<UserEntity> {
    @Throws(SQLException::class)
    override fun mapRow(rs: ResultSet, rowNum: Int): UserEntity {
        val userEntity = UserEntity()
        userEntity.id = rs.getString("ID")
        userEntity.firstName = rs.getString("FIRST_NAME")
        userEntity.secondName = rs.getString("SECOND_NAME")
        userEntity.biography = rs.getString("BIOGRAPHY")
        userEntity.city = rs.getString("CITY")
        userEntity.password = rs.getString("PASSWORD")
        userEntity.sex = rs.getString("SEX")
        userEntity.birthdate = rs.getDate("BIRTH_DATE").toLocalDate()
        userEntity.age = rs.getInt("age")
        return userEntity
    }
}
