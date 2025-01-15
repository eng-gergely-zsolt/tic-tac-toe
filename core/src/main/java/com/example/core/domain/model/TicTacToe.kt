package com.example.core.domain.model

import androidx.room.PrimaryKey
import java.time.LocalDateTime

data class TicTacToe(
    val draws: Int,

    val hostWins: Int,

    val guestWins: Int,

    val startDateTime: LocalDateTime
)
