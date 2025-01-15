package com.example.presentation.game

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

sealed interface TicTacToeSideEffect {
    sealed interface Navigation : TicTacToeSideEffect {
        data object NavigateBack : Navigation
    }
}
