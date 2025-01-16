package com.example.presentation.history

import com.example.core.domain.model.TicTacToe

class HistoryContract {
    data class HistoryState(
        val isLoading: Boolean,
        val ticTacToeMatches: List<TicTacToe>
    )
}
