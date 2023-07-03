package ru.zahv.alex.socialnetwork.utils

import java.sql.Timestamp
import java.time.LocalDateTime

fun convertSqlDateToLocalDateTime(date: Timestamp?): LocalDateTime? {
    if (date == null) {
        return null
    }
    return date.toLocalDateTime()
}