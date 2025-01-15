package com.example.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents the tic tac toe table in the database.
 */
@Entity(tableName = "tic_tac_toe")
data class TicTacToeEntity(
    val draws: Int,

    val hostWins: Int,

    val guestWins: Int,

    @PrimaryKey
    val startDateTime: Long
)
