package com.example.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateTimeUtils {
    fun formatLocalDateTimeToTime(localDateTime: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return localDateTime.format(formatter)
    }

    fun formatLocalDateTimeToDate(localDateTime: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        return localDateTime.format(formatter)
    }
}
