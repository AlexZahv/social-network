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
import ru.zahv.alex.socialnetwork.business.persistance.domain.PostEntity
import ru.zahv.alex.socialnetwork.business.persistance.repository.PostDao
import ru.zahv.alex.socialnetwork.utils.SecurityContextHolder
import ru.zahv.alex.socialnetwork.web.dto.posts.PostCreateRequestDTO
import ru.zahv.alex.socialnetwork.web.dto.posts.PostUpdateRequestDTO
import java.math.BigDecimal
import java.sql.ResultSet
import java.sql.SQLException
import java.time.LocalDateTime
import java.util.*

@Repository
@ConditionalOnProperty(name = ["orm.enabled"], havingValue = "false", matchIfMissing = true)
class PostJDBCDao(
    @Qualifier("masterTemplate") private val masterTemplate: NamedParameterJdbcTemplate,
    @Qualifier("slaveTemplate") private val slaveTemplate: NamedParameterJdbcTemplate
) : PostDao {

    override fun getPost(id: String): PostEntity? {
        val namedParameters: SqlParameterSource = MapSqlParameterSource().addValue("id", id)
        val postList = slaveTemplate.query(
            "SELECT * from posts where id = :id",
            namedParameters,
            PostRowMapper(),
        )
        if (postList.isNotEmpty()) {
            return postList[0]
        }

        return null
    }

    @Transactional
    override fun createPost(createRequestDTO: PostCreateRequestDTO): String {
        val id = UUID.randomUUID().toString()
        val entity =
            PostEntity(
                id,
                createRequestDTO.text,
                SecurityContextHolder.getCurrentUser(),
                LocalDateTime.now()
            )
        val namedParameters: SqlParameterSource = BeanPropertySqlParameterSource(entity)
        masterTemplate.update(
            "insert into " +
                    "posts (id, post_text, author_id, create_date) " +
                    "values (:id, :text, :authorId, :createDate) ",
            namedParameters,
        )

        return id
    }

    @Transactional
    override fun updatePost(postUpdateRequestDTO: PostUpdateRequestDTO) {
        val entity =
            PostEntity(
                postUpdateRequestDTO.id,
                postUpdateRequestDTO.text,
                SecurityContextHolder.getCurrentUser()
            )
        val namedParameters: SqlParameterSource = BeanPropertySqlParameterSource(entity)
        masterTemplate.update(
            "update " +
                    "posts set post_text=:text " +
                    "where id=:id and author_id=:authorId ",
            namedParameters,
        )
    }

    @Transactional
    override fun deletePost(id: String) {
        val namedParameters: SqlParameterSource = MapSqlParameterSource()
            .addValue("id", id)
            .addValue("authorId", SecurityContextHolder.getCurrentUser())

        masterTemplate.update(
            "delete from posts " +
                    "where id=:id and author_id=:authorId ",
            namedParameters,
        )
    }

    override fun getPostFeed(userId: String, offset: BigDecimal, limit: BigDecimal): List<PostEntity> {
        val namedParameters: SqlParameterSource = MapSqlParameterSource()
            .addValue("offset", offset.longValueExact())
            .addValue("limit", limit.longValueExact())
            .addValue("userId", userId)

        return slaveTemplate.query(
            "select p.* from posts p " +
                    "join (" +
                    "select p1.id from posts p1 join friendship fr on p1.author_id = fr.friend_id " +
                    "where fr.user_id=:userId " +
                    "order by p1.create_date desc limit :limit offset :offset" +
                    ") as b " +
                    "on b.id = p.id",
            namedParameters,
            PostRowMapper(),
        )
    }

    override fun getAllCacheKeyList(userId: String): List<String> {
        val namedParameters: SqlParameterSource = MapSqlParameterSource()
            .addValue("userId", userId)

        return slaveTemplate.query(
            "select 'POSTS_' || fr.friend_id as \"key\" " +
                    "from friendship fr " +
                    "join auth_tokens a on fr.friend_id = a.user_id " +
                    "where fr.user_id=:userId " +
                    "and a.expire_date> now() - interval '10 minutes' ",
            namedParameters,
            PostFriendRowMapper()
        )
    }
}


class PostRowMapper : RowMapper<PostEntity> {
    @Throws(SQLException::class)
    override fun mapRow(rs: ResultSet, rowNum: Int): PostEntity {
        val postEntity = PostEntity()
        postEntity.id = rs.getString("ID")
        postEntity.text = rs.getString("POST_TEXT")
        postEntity.authorId = rs.getString("AUTHOR_ID")
        postEntity.createDate = rs.getTimestamp("CREATE_DATE").toLocalDateTime()
        return postEntity
    }
}

class PostFriendRowMapper : RowMapper<String> {
    @Throws(SQLException::class)
    override fun mapRow(rs: ResultSet, rowNum: Int): String {
        return rs.getString("KEY")
    }
}