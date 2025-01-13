package com.example.presentation

import com.example.core.domain.model.Field
import com.example.core.domain.model.Player

class TicTacToeContract {
    data class TicTacToeState(
        val draws: Int,
        val hostWins: Int,
        val guestWins: Int,
        val winner: Player?,
        val isDraw: Boolean,
        val currentPlayer: Player,
        val board: MutableSet<Field>,
        val showEndGameDialog: Boolean
    )
}
