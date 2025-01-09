package com.example.tic_tac_toe.navigation

import kotlinx.serialization.Serializable

sealed class NavigationRoute {
    @Serializable
    data object TicTacToeNavigationRoute : NavigationRoute()
}
