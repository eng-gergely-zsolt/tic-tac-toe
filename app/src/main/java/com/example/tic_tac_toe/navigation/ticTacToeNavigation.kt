package com.example.tic_tac_toe.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.presentation.TicTacToeScreen

fun NavGraphBuilder.ticTacToeNavigation(
    navController: NavController
) {
    composable<NavigationRoute.TicTacToeNavigationRoute> {
        TicTacToeScreen()
    }
}
