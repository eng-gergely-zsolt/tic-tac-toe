package com.example.presentation.landing

sealed interface LandingSideEffect {
    sealed interface Navigation : LandingSideEffect {
        data object NavigateToHistory : Navigation

        data class NavigateToTicTacToe(val boardSize: Int) : Navigation
    }
}
