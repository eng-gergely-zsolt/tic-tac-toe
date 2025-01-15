package com.example.tic_tac_toe.navigation

import kotlinx.serialization.Serializable

sealed class NavigationRoute {
    @Serializable
    data object LandingNavigationRoute : NavigationRoute()

    @Serializable
    data object HistoryNavigationRoute : NavigationRoute()

    @Serializable
    data class TicTacToeNavigationRoute(val boardSize: Int) : NavigationRoute()
}
